
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CFGtoCNF {

	public static String[][] grammar = new String[20][20]; // to store entered grammar
	public String input;
	public String string;
	public int lineCount;
	// map variable with production ( variable -> production)
	public Map<String, List<String>> mapVariableProduction = new LinkedHashMap<>();


	public void setString(String string) {
        this.string = string;
    }

    public void setInputandLineCount(String input, int lineCount) {
        this.input = input;
        this.lineCount = lineCount;

    }

   

    public Map<String, List<String>> getMapVariableProduction() {
        return mapVariableProduction;
    }

    public void convertCFGtoCNF() {
    	insertNewStartSymbol();
        convertStringtoMap();
        eliminateEpselon();
        eliminateUnitProduction();
        eliminateTerminals();
        eliminateLonger2();
        printCNF();
    }
    
    private void eliminateUnitProduction() {
    	


        Iterator itr3 = mapVariableProduction.entrySet().iterator();

        while (itr3.hasNext()) {
            Map.Entry entry = (Map.Entry) itr3.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionRow.size(); i++) {
                if (productionRow.get(i).contains(entry.getKey().toString())) {
                    productionRow.remove(entry.getKey().toString());
                }
            }
        }
    	
    	 System.out.println("Eliminate Unit Production ... ");

         for (int i = 0; i < lineCount; i++) {
             removeSingleVariable();
         }

         printInput();

    }
    
   

    private void eliminateLonger2() {

        System.out.println("Break variable strings longer than 2... ");

        for (int i = 0; i < lineCount; i++) {
            breakLonger2();
        }

        printInput();

    }

    private void eliminateEpselon() {

        System.out.println("\nEliminate Epselon....");

        for (int i = 0; i < lineCount; i++) {
            removeEpselon();
        }

        printInput();

    }

    private String[] splitEnter(String input) {

        String[] tmpArray = new String[lineCount];
        for (int i = 0; i < lineCount; i++) {
            tmpArray = input.split("\\n");
        }
        return tmpArray;
    }

    private void printInput() {

        Iterator it = mapVariableProduction.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " -> " + pair.getValue());
        }

        System.out.println(" ");
    }
    private void printCNF() {
    	System.out.println("Chomsky Normal Form: ...");
        Iterator it = mapVariableProduction.entrySet().iterator();
        it.next();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            System.out.println(pair.getKey() + " -> " + pair.getValue());
        }

        System.out.println(" ");
    }

    private void convertStringtoMap() {

        String[] splitedEnterInput = splitEnter(input);


        for (int i = 0; i < splitedEnterInput.length; i++) {

            String[] tempString = splitedEnterInput[i].split("-|\\|");
            String variable = tempString[0].trim();

            String[] production = Arrays.copyOfRange(tempString, 1, tempString.length);
            List<String> productionList = new ArrayList<String>();

            // trim the empty space
            for (int k = 0; k < production.length; k++) {
                production[k] = production[k].trim();
            }

            // import array into ArrayList
            for (int j = 0; j < production.length; j++) {
                productionList.add(production[j]);
            }

            //insert element into map
            mapVariableProduction.put(variable, productionList);
        }
    }


	public String epselonFound = "";
    private void removeEpselon() {

        Iterator itr = mapVariableProduction.entrySet().iterator();
        Iterator itr2 = mapVariableProduction.entrySet().iterator();

        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.contains("€")) {
                if (productionRow.size() > 1) {
                    productionRow.remove("€");
                    epselonFound = entry.getKey().toString();


                } else {

                    // remove if less than 1
                    epselonFound = entry.getKey().toString();
                    mapVariableProduction.remove(epselonFound);
                }
            }
        }

        // find B and eliminate them
        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {
                    if (epselonFound.equals(Character.toString(productionList.get(i).charAt(j)))) {

                        if (temp.length() == 2) {

                            // remove specific character in string
                            temp = temp.replace(epselonFound, "");

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(temp);
                            }

                        } else if (temp.length() == 3) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
                            }

                        } else if (temp.length() == 4) {

                            String deletedTemp = new StringBuilder(temp).deleteCharAt(j).toString();

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains(deletedTemp)) {
                                mapVariableProduction.get(entry.getKey().toString()).add(deletedTemp);
                            }
                        } else {

                            if (!mapVariableProduction.get(entry.getKey().toString()).contains("€")) {
                                mapVariableProduction.get(entry.getKey().toString()).add("€");
                            }
                        }
                    }
                }
            }
        }
    }

    private void insertNewStartSymbol() {

        String newStart = "S0";
        ArrayList<String> newProduction = new ArrayList<>();
        newProduction.add("S");

        mapVariableProduction.put(newStart, newProduction);
    }
    
    private void removeSingleVariable() {

        Iterator itr4 = mapVariableProduction.entrySet().iterator();
        String key = null;


        while (itr4.hasNext()) {

            Map.Entry entry = (Map.Entry) itr4.next();
            Set set = mapVariableProduction.keySet();
            ArrayList<String> keySet = new ArrayList<String>(set);
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {

                    for (int k = 0; k < keySet.size(); k++) {
                        if (keySet.get(k).equals(temp)) {

                            key = entry.getKey().toString();
                            List<String> productionValue = mapVariableProduction.get(temp);
                            productionList.remove(temp);

                            for (int l = 0; l < productionValue.size(); l++) {
                                mapVariableProduction.get(key).add(productionValue.get(l));
                            }
                        }
                    }
                }
            }
        }
    }

    private Boolean isTwoTerminal(Map<String, List<String>> map, String key) {

        Boolean notFound = true;

        Iterator itr = map.entrySet().iterator();
        outerloop:

        while (itr.hasNext()) {

            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            for (int i = 0; i < productionList.size(); i++) {
                if (productionList.size() < 2) {

                    if (productionList.get(i).equals(key)) {
                        notFound = false;
                        break outerloop;
                    } else {
                        notFound = true;
                    }
                }
            }
        }

        return notFound;
    }

    private void eliminateTerminals() {

        System.out.println("Eliminate terminals ... ");

        Iterator itr5 = mapVariableProduction.entrySet().iterator();
        String key = null;
        int asciiBegin = 71; //G

        Map<String, List<String>> tempList = new LinkedHashMap<>();

        while (itr5.hasNext()) {

            Map.Entry entry = (Map.Entry) itr5.next();
            Set set = mapVariableProduction.keySet();

            ArrayList<String> keySet = new ArrayList<String>(set);
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();
            Boolean found1 = false;
            Boolean found2 = false;
            Boolean found = false;


            for (int i = 0; i < productionList.size(); i++) {
                String temp = productionList.get(i);

                for (int j = 0; j < temp.length(); j++) {

                    if (temp.length() == 3) {

                        String newProduction = temp.substring(1, 3); // SA

                        if (isTwoTerminal(tempList, newProduction) && isTwoTerminal(mapVariableProduction, newProduction)) {
                            found = true;
                        } else {
                            found = false;
                        }

                        if (found) {

                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }

                    } else if (temp.length() == 2) { // if only two substring

                        for (int k = 0; k < keySet.size(); k++) {

                            if (!keySet.get(k).equals(Character.toString(productionList.get(i).charAt(j)))) { // if substring not equals to keySet
                                found = false;

                            } else {
                                found = true;
                                break;
                            }

                        }

                        if (!found) {
                            String newProduction = Character.toString(productionList.get(i).charAt(j));

                            if (isTwoTerminal(tempList, newProduction) && isTwoTerminal(mapVariableProduction, newProduction)) {

                                ArrayList<String> newVariable = new ArrayList<>();
                                newVariable.add(newProduction);
                                key = Character.toString((char) asciiBegin);

                                tempList.put(key, newVariable);

                                asciiBegin++;

                            }
                        }
                    } else if (temp.length() == 4) {

                        String newProduction1 = temp.substring(0, 2); // SA
                        String newProduction2 = temp.substring(2, 4); // SA

                        if (isTwoTerminal(tempList, newProduction1) && isTwoTerminal(mapVariableProduction, newProduction1)) {
                            found1 = true;
                        } else {
                            found1 = false;
                        }

                        if (isTwoTerminal(tempList, newProduction2) && isTwoTerminal(mapVariableProduction, newProduction2)) {
                            found2 = true;
                        } else {
                            found2 = false;
                        }


                        if (found1) {

                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction1);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }

                        if (found2) {
                            ArrayList<String> newVariable = new ArrayList<>();
                            newVariable.add(newProduction2);
                            key = Character.toString((char) asciiBegin);

                            tempList.put(key, newVariable);
                            asciiBegin++;
                        }
                    }
                }
            }
        }
        mapVariableProduction.putAll(tempList);
        printInput();
    }

    private void breakLonger2() {

        Iterator itr = mapVariableProduction.entrySet().iterator();
        ArrayList<String> keyList = new ArrayList<>();
        Iterator itr2 = mapVariableProduction.entrySet().iterator();

        // obtain key that use to eliminate two terminal and above
        while (itr.hasNext()) {
            Map.Entry entry = (Map.Entry) itr.next();
            ArrayList<String> productionRow = (ArrayList<String>) entry.getValue();

            if (productionRow.size() < 2) {
                keyList.add(entry.getKey().toString());
            }
        }

        // find more than three terminal or combination of variable and terminal to eliminate them
        while (itr2.hasNext()) {

            Map.Entry entry = (Map.Entry) itr2.next();
            ArrayList<String> productionList = (ArrayList<String>) entry.getValue();

            if (productionList.size() > 1) {
                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    for (int j = 0; j < temp.length(); j++) {

                        if (temp.length() > 2) {
                            String stringToBeReplaced1 = temp.substring(j, temp.length());
                            String stringToBeReplaced2 = temp.substring(0, temp.length() - j);

                            for (String key : keyList) {

                                List<String> keyValues = new ArrayList<>();
                                keyValues = mapVariableProduction.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];

                                if (stringToBeReplaced1.equals(value)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced1, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                } else if (stringToBeReplaced2.equals(value)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(stringToBeReplaced2, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }
                        } else if (temp.length() == 2) {

                            for (String key : keyList) {

                                List<String> keyValues = new ArrayList<>();
                                keyValues = mapVariableProduction.get(key);
                                String[] values = keyValues.toArray(new String[keyValues.size()]);
                                String value = values[0];


                                for (int pos = 0; pos < temp.length(); pos++) {
                                    String tempChar = Character.toString(temp.charAt(pos));


                                    if (value.equals(tempChar)) {

                                        mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                        temp = temp.replace(tempChar, key);

                                        if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                            mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            } else if (productionList.size() == 1) {

                for (int i = 0; i < productionList.size(); i++) {
                    String temp = productionList.get(i);

                    if (temp.length() == 2) {

                        for (String key : keyList) {

                            List<String> keyValues = new ArrayList<>();
                            keyValues = mapVariableProduction.get(key);
                            String[] values = keyValues.toArray(new String[keyValues.size()]);
                            String value = values[0];


                            for (int pos = 0; pos < temp.length(); pos++) {
                                String tempChar = Character.toString(temp.charAt(pos));


                                if (value.equals(tempChar)) {

                                    mapVariableProduction.get(entry.getKey().toString()).remove(temp);
                                    temp = temp.replace(tempChar, key);

                                    if (!mapVariableProduction.get(entry.getKey().toString()).contains(temp)) {
                                        mapVariableProduction.get(entry.getKey().toString()).add(i, temp);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    
}
