package main.controler.parse;

/*
 *  https://docs.oracle.com/javase/8/docs/api/index.html?java/util/StringTokenizer.html
 */

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SimpleParser implements IParser {

    @Override
    public List<String> parse(String input) {
        StringTokenizer st = new StringTokenizer(input);
        List<String> output = new ArrayList<>();
        while(st.hasMoreTokens()) {
            output.add(st.nextToken());
        }
        return output;
    }
}
