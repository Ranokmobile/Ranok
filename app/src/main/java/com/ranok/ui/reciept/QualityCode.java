package com.ranok.ui.reciept;

public enum QualityCode {
    NORM("Нормальные"), X_CUSTOMER("Брак клиента"), X_PRODUCTION("Брак производства"),
    X_TRANSPORT("Брак транспортировки"), X_WAREHOUSE("Брак склада"),
    EXAM("Образцы"), RETURN("Ликвидный возврат");

    String label;
    QualityCode(String label) {
        this.label = label;
    }

    public static QualityCode getByLabel(String lbl){
        for(QualityCode e : QualityCode.values()){
            if(lbl.equals(e.label)) return e;
        }
        return null;
    }

}
