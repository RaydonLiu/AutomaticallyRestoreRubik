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
                case "U":tips.add("U:������ʱ����ת");break;
                case "Ui":tips.add("Ui:����˳ʱ����ת");break;
                case "D":tips.add("D:�ײ���ʱ����ת");break;
                case "Di":tips.add("Di:�ײ�˳ʱ����ת");break;
                case "L":tips.add("L:���˳ʱ����ת");break;
                case "Li":tips.add("Li:�����ʱ����ת");break;
                case "R":tips.add("R:�Ҳ�˳ʱ����ת");break;
                case "Ri":tips.add("Ri:�Ҳ���ʱ����ת");break;
                case "F":tips.add("F:ǰ��˳ʱ����ת");break;
                case "Fi":tips.add("Fi:ǰ����ʱ����ת");break;
                case "B":tips.add("B:���˳ʱ����ת");break;
                case "Bi":tips.add("Bi:�����ʱ����ת");break;
            }
        }
        return tips;
    }
}
