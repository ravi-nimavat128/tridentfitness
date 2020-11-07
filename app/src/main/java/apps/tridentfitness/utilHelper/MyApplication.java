package apps.tridentfitness.utilHelper;

import java.util.ArrayList;

import apps.tridentfitness.getset.SelectItem;


public class MyApplication {
    private static MyApplication mInstance = null;
    ArrayList<SelectItem> Step1;
    ArrayList<SelectItem> Step2;
    ArrayList<SelectItem> Step3;
    ArrayList<SelectItem> Step4;

    ArrayList<SelectItem> mainDragList;

    public ArrayList<SelectItem> getMainDragList() {
        return mainDragList;
    }

    public void setMainDragList(ArrayList<SelectItem> mainDragList) {
        this.mainDragList = mainDragList;
    }

    public ArrayList<SelectItem> getStep1() {
        return Step1;
    }

    public void setStep1(ArrayList<SelectItem> step1) {
        this.Step1 = step1;
    }

    public ArrayList<SelectItem> getStep2() {
        return Step2;
    }

    public void setStep2(ArrayList<SelectItem> step2) {
        this.Step2 = step2;
    }

    public ArrayList<SelectItem> getStep3() {
        return Step3;
    }

    public void setStep3(ArrayList<SelectItem> step3) {
        this.Step3 = step3;
    }

    public ArrayList<SelectItem> getStep4() {
        return Step4;
    }

    public void setStep4(ArrayList<SelectItem> step4) {
        this.Step4 = step4;
    }
    public static MyApplication getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }
}
