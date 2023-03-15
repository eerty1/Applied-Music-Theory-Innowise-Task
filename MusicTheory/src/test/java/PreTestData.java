public class PreTestData {
    public String[][] getIntervalConstructionInputArray() {
        return new String[][]{
                {"M2","C","asc"},
                {"P5","B","asc"},
                {"m2","Bb","dsc"},
                {"M3","Cb","dsc"},
                {"P4","G#","dsc"},
                {"m3","B","dsc"},
                {"m2","Fb","asc"},
                {"M2","E#","dsc"},
                {"P4","E","dsc"},
                {"m2","D#","asc"},
                {"M7","G","asc"},
        };
    }

    public String[] getReturnValuesForIntervalConstruction() {
        return new String[]{"D", "F#", "A", "Abb", "D#", "G#", "Gbb", "D#", "B", "E", "F#"};
    }

    public String[][] getIntervalIdentificationInputArray() {
        return new String[][] {
                {"C", "D"},
                {"B","F#","asc"},
                {"Fb","Gbb"},
                {"G","F#","asc"},
                {"Bb","A","dsc"},
                {"Cb","Abb","dsc"},
                {"G#","D#","dsc"},
                {"E","B","dsc"},
                {"E#","D#","dsc"},
                {"B","G#","dsc"}
        };
    }

    public String[] getReturnValuesForIntervalIdentification() {
        return new String[] {"M2", "P5", "m2", "M7", "m2", "M3", "P4", "P4", "M2", "m3"};
    }
}
