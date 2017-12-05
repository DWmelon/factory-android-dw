package com.yongheng.application;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.yongheng.model.BusinessModel;
import com.yongheng.model.CountryModel;
import com.yongheng.model.DataModel;
import com.yongheng.model.ProductModel;
import com.yongheng.module.egg.OnDataFinishListener;
import com.yongheng.module.home.listener.OnLoadDataFinishListener;
import com.yongheng.network.IRequest;
import com.yongheng.network.IRequestCallback;
import com.yongheng.storage.StorageManager;
import com.yongheng.storage.TaskExecutor;
import com.yongheng.utils.CharUtil;
import com.yongheng.utils.FileUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * Created by melon on 2017/4/4.
 */

public class DataManager {

    private static final String DATA_NAME = "data.xls";

    private static final String DATA_NAME_2 = "data2.xls";

    private static final String URL_UPLOAD = "http://192.168.31.164:12344/data/country/update";

    private Context context;

    private DataModel dataModel = new DataModel();
    private List<BusinessModel> modelList = dataModel.getBusinessModelList();
    private HashMap<String,BusinessModel> modelHashMap = new HashMap<>();

    private OnLoadDataFinishListener onLoadDataFinishListener;
    private boolean isLoadFinish = false;

    public void registerLoadDataFinishListener(OnLoadDataFinishListener listener){
        onLoadDataFinishListener = listener;
    }

    public boolean isLoadFinish() {
        return isLoadFinish;
    }

    public void init(Context context){
        this.context = context;
        initData();
    }

    private void initData(){
        getAlarmDataFromFile();

    }

    private List<CountryModel> countryModelList = new ArrayList<>();

    public List<CountryModel> getCountryModelList() {
        return countryModelList;
    }

    public void setCountryModelList(List<CountryModel> countryModelList) {
        this.countryModelList = countryModelList;
    }

    public void getDataFromXls(){
        try {
            modelHashMap.clear();
            modelList.clear();
            /**
             * 后续考虑问题,比如Excel里面的图片以及其他数据类型的读取
             **/
            long time = System.currentTimeMillis();
            InputStream is = context.getAssets().open(DATA_NAME);

            WorkbookSettings settings = new WorkbookSettings ();
            settings.setEncoding("UTF-8");
            settings.setWriteAccess(null);
            Workbook book = Workbook.getWorkbook(is,settings);

            BusinessModel model = new BusinessModel();
            List<ProductModel> productModelList = new ArrayList<>();
            ProductModel proModel = new ProductModel();

            for (int k = 0;k<book.getNumberOfSheets();k++){
                Sheet sheet = book.getSheet(k);
                int Rows = sheet.getRows();
                int Cols = sheet.getColumns();

                for (int i = 1; i < Rows; ++i) {
                    boolean isFirstData = false;
                    for (int j = 0; j < Cols; ++j) {

                        if (j == 0){
                            String str = sheet.getCell(j,i).getContents();
                            if (!str.equals("")){
                                if (model.getBusinessName() != null){
                                    model.setFirstPy(CharUtil.cn2py(model.getBusinessName()));
                                    modelHashMap.put(model.getBusinessName(),model);
                                    modelList.add(model);
                                }
                                model = new BusinessModel();
                                model.setBusinessName(str);
                                productModelList = model.getProductList();
                                isFirstData = true;
                            }
                        }else if (j == 1){
                            String str = sheet.getCell(j,i).getContents();
                            if (str.equals("")){
                                i++;
                                j=-1;
                                continue;
                            }
                            proModel = new ProductModel();
                            proModel.setProName(str);
                        }else if (j == 2){
                            String str = sheet.getCell(j,i).getContents();
                            proModel.setPrice(str);
                        }else if (j == 3){
                            String str = sheet.getCell(j,i).getContents();
                            if (isFirstData){
                                model.setRemark(str);
                                isFirstData = false;
                                proModel.setRemark("");
                                productModelList.add(proModel);
                            }else{
                                proModel.setRemark(str);
                                productModelList.add(proModel);
                            }

                        }

                    }
                }
            }
            book.close();
            String ti = String.valueOf(System.currentTimeMillis()-time);
            Log.i("time",ti);
            Collections.sort(modelList, new Comparator<BusinessModel>() {
                @Override
                public int compare(BusinessModel businessModel, BusinessModel t1) {
                    return businessModel.getFirstPy().compareTo(t1.getFirstPy());
                }
            });
            dataModel.setBusinessModelList(modelList);
            saveAlarmDataToFile();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void getDataFromXls2(){
        try {
            /**
             * 后续考虑问题,比如Excel里面的图片以及其他数据类型的读取
             **/
            long time = System.currentTimeMillis();
            InputStream is = context.getAssets().open(DATA_NAME_2);

            WorkbookSettings settings = new WorkbookSettings ();
            settings.setEncoding("UTF-8");
            settings.setWriteAccess(null);
            Workbook book = Workbook.getWorkbook(is,settings);

            List<CountryModel> countryModelList = new ArrayList<>();
            CountryModel countryModel = new CountryModel();

            String state = "";

            Sheet sheet = book.getSheet(0);
            int Rows = sheet.getRows();
            int Cols = sheet.getColumns();

            for (int i = 1; i < Rows; ++i) {

                if (TextUtils.isEmpty(sheet.getCell(0,i).getContents())){
                    break;
                }

                for (int j = 0; j < Cols; ++j) {

                    if (j == 0){
                        String str = sheet.getCell(j,i).getContents();
                        if (str.equals("非洲")){
                            state = "africa";
                            break;
                        }else if (str.equals("美洲")){
                            state = "america";
                            break;
                        }else if (str.equals("亚洲")){
                            state = "asia";
                            break;
                        }else if (str.equals("欧洲")){
                            state = "europe";
                            break;
                        }else if (str.equals("大洋洲")){
                            state = "oceania";
                            break;
                        }else {
                            countryModel = new CountryModel();
                            countryModel.setCityNameE(str);
                        }
                    }else if (j == 1){
                        String str = sheet.getCell(j,i).getContents();
                        countryModel.setDiffTime(Float.valueOf(str));
                    }else if (j == 2){
                        String str = sheet.getCell(j,i).getContents();
                        countryModel.setNationName(str);
                    }else if (j == 3){
                        String str = sheet.getCell(j,i).getContents();
                        countryModel.setCityName(str);
                    }else if (j == 4){
                        String str = sheet.getCell(j,i).getContents();
                        countryModel.setLogo(str);
                    }else if (j == 5){
                        String str = sheet.getCell(j,i).getContents();
                        countryModel.setSaveTimeAround(str);
                    }else if (j == 6){
                        String str = sheet.getCell(j,i).getContents();
                        countryModel.setZoneNum(str);
                        countryModel.setState(state);
                        countryModelList.add(countryModel);
                        break;
                    }

                }
            }
            book.close();
            String ti = String.valueOf(System.currentTimeMillis()-time);
            Log.i("time",ti);
            this.countryModelList = countryModelList;
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    /**
     * 保存信息到文件
     */
    public synchronized void saveAlarmDataToFile(){
        TaskExecutor.getInstance().post(new Runnable() {
            @Override
            public void run() {
                if (dataModel != null) {
                    String path = StorageManager.getInstance().getPackageFiles() + "alarm";
                    FileUtil.writeObjectToPath(dataModel, path);
                }
            }
        });
    }

    /**
     * 从文件中读取信息
     */
    public void getAlarmDataFromFile(){
        TaskExecutor.getInstance().post(new Runnable() {
            @Override
            public void run() {
                synchronized (DataManager.class) {

                    String path = StorageManager.getInstance().getPackageFiles() + "alarm";
                    Object object = FileUtil.readObjectFromPath(path);

                    if (object != null && object instanceof DataModel) {
                        dataModel = (DataModel) object;
                        modelHashMap.clear();
                        for (BusinessModel model : dataModel.getBusinessModelList()){
                            modelHashMap.put(model.getBusinessName(),model);
                        }
                        modelList = dataModel.getBusinessModelList();
                    }


                    isLoadFinish = true;
                    if (onLoadDataFinishListener != null){
                        onLoadDataFinishListener.onLoadDataFinish();
                    }


                }
            }
        });
    }

    public List<BusinessModel> getModelList() {
        return modelList;
    }

    public void setModelList(List<BusinessModel> modelList) {
        this.modelList = modelList;
        this.dataModel.setBusinessModelList(modelList);
        modelHashMap.clear();
        for (BusinessModel model : modelList){
            modelHashMap.put(model.getBusinessName(),model);
        }
        saveAlarmDataToFile();
    }

    public HashMap<String, BusinessModel> getModelHashMap() {
        return modelHashMap;
    }

    public void setModelHashMap(HashMap<String, BusinessModel> modelHashMap) {
        this.modelHashMap = modelHashMap;
    }

    public void deleteBusiness(int position){
        BusinessModel model = dataModel.getBusinessModelList().get(position);

        modelHashMap.remove(model.getBusinessName());
        modelList.remove(model);

    }

    public void updateDiffTime(CountryModel model, final OnDataFinishListener listener) {

        final IRequest request = (IRequest) MyClient.getMyClient().getService(MyClient.SERVICE_HTTP_REQUEST);
        if (request == null) {
            return;
        }

        HashMap<String,String> map = new HashMap<>();
        map.put("cityName",model.getCityName());
        map.put("zoneNum",model.getZoneNum());
        map.put("savingTimeAround",model.getSaveTimeAround());
        map.put("nationName",model.getNationName());
        map.put("cityNameE",model.getCityNameE());
        map.put("diffTime",String.valueOf(model.getDiffTime()));
        map.put("logo",model.getLogo());
        map.put("state",model.getState());

        request.sendRequestForPostWithJson(URL_UPLOAD, map, new IRequestCallback() {
            @Override
            public void onResponseSuccess(JSONObject jsonObject) {
                if (listener == null) {
                    return;
                }

                if (jsonObject == null) {
                    listener.onHandleDataFinish();
                    return;
                }

                listener.onHandleDataFinish();

            }

            @Override
            public void onResponseSuccess(String str) {

            }

            @Override
            public void onResponseError(int code) {
                if (listener != null) {
                    listener.onHandleDataFinish();
                }
            }
        });


    }


}
