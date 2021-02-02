public class Scoop implements ActionWithWater{
    private int countSuccess = 0;
    private int valueSuccess = 0;
    private int countFail = 0;
    private int valueFail = 0;
    public void actionProgress(int value, boolean resultAction) {
        if (resultAction) {
            countSuccess++;
            valueSuccess += value;
        } else {
            countFail++;
            valueFail += value;
        }
    }

    public void setCountSuccess(int countSuccess) {
        this.countSuccess = countSuccess;
    }

    public void setValueSuccess(int valueSuccess) {
        this.valueSuccess = valueSuccess;
    }

    public void setCountFail(int countFail) {
        this.countFail = countFail;
    }

    public void setValueFail(int valueFail) {
        this.valueFail = valueFail;
    }

    public int getCountSuccess() {
        return countSuccess;
    }

    public int getValueSuccess() {
        return valueSuccess;
    }

    public int getCountFail() {
        return countFail;
    }

    public int getValueFail() {
        return valueFail;
    }
}