package engine;

import java.util.ArrayList;

/**
 * Created by Liu on 2016/12/24 0024.
 */
public class RecoverTips {
    public static ArrayList<String> getTips(ArrayList<String> recoverWay){
        ArrayList<String> tips = new ArrayList<String>();
        for(String way : recoverWay){
            switch (way){
                case "U":tips.add("U:顶层逆时针旋转");break;
                case "Ui":tips.add("Ui:顶层顺时针旋转");break;
                case "D":tips.add("D:底层逆时针旋转");break;
                case "Di":tips.add("Di:底层顺时针旋转");break;
                case "L":tips.add("L:左层顺时针旋转");break;
                case "Li":tips.add("Li:左层逆时针旋转");break;
                case "R":tips.add("R:右层顺时针旋转");break;
                case "Ri":tips.add("Ri:右层逆时针旋转");break;
                case "F":tips.add("F:前层顺时针旋转");break;
                case "Fi":tips.add("Fi:前层逆时针旋转");break;
                case "B":tips.add("B:后层顺时针旋转");break;
                case "Bi":tips.add("Bi:后层逆时针旋转");break;
            }
        }
        return tips;
    }
}
