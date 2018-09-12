package com.ranok.ui.reciept;

public enum QualityCode {
    NORM("Нормальные", 0), X_CUSTOMER("Брак клиента", 1), X_PRODUCTION("Брак производства", 2),
    X_TRANSPORT("Брак транспортировки", 3), X_WAREHOUSE("Брак склада", 4),
    EXAM("Образцы", 5), RETURN("Ликвидный возврат", 6), UNKNOWN("", 7);

    String label;
    int npp;

    QualityCode(String label, int npp) {
        this.label = label;
        this.npp = npp;
    }

    public static QualityCode getByLabel(String lbl){
        for(QualityCode e : QualityCode.values()){
            if(lbl.equals(e.label)) return e;
        }
        return null;
    }

    public static QualityCode getByNpp(int npp){
        for(QualityCode e : QualityCode.values()){
            if(e.npp == npp) return e;
        }
        return UNKNOWN;
    }

}
