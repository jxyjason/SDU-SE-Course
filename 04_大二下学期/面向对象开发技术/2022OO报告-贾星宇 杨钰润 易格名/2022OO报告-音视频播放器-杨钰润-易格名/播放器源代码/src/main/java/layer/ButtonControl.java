package layer;


public  class ButtonControl extends MyRenderer {
    public static int iconChange(int clickCount, RButton btn, String icon1, String icon2){
        if(clickCount % 2 == 0){
            btn.setIcon(icon1, btn);
            clickCount++;
        } else{
            btn.setIcon(icon2, btn);
            clickCount++;
        }
        return clickCount;
    }

    static int iconChange(int clickCount, RButton btn, String icon1, String icon2, String icon3){
        if(clickCount % 3 == 0){
            btn.setIcon(icon1, btn);
            clickCount++;
        } else if (clickCount % 3 == 1){
            btn.setIcon(icon2, btn);
            clickCount++;
        } else{
            btn.setIcon(icon3, btn);
            clickCount++;
        }
        return clickCount;
    }

    public static int iconChange(int clickCount, RButton btn, String icon1, String icon2, String icon3, String icon4){
        if(clickCount % 4 == 0){
            btn.setIcon(icon1, btn);
            clickCount++;
        } else if (clickCount % 4 == 1){
            btn.setIcon(icon2, btn);
            clickCount++;
        } else if (clickCount % 4 == 2){
            btn.setIcon(icon3, btn);
            clickCount++;
        } else {
            btn.setIcon(icon4, btn);
            clickCount++;
        }
        return clickCount;
    }

    public static void refresh(RButton btn){
        btn.setIcon("D:\\UIresources\\play.png",btn);
    }

    public void restart(){

    }
}


