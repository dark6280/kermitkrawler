package com.example.a6;

public class users {
    int score,profilepic,totalscore,bestscore;
    String ID,username,email;
    boolean ppic1,ppic2,ppic3,ppic4,ppic5,ppic6,ppic7,ppic8;
    float best_lvl1,best_lvl2,best_lvl3,best_lvl4,best_boss_battle;


    public users(int score,int totalscore,String username,String email,int profilepic,String ID,boolean ppic1,boolean ppic2,boolean ppic3,boolean ppic4,boolean ppic5,boolean ppic6,boolean ppic7,boolean ppic8,float best_lvl1,float best_lvl2,float best_lvl3,float best_lvl4,float best_boss_battle,int bestscore) {
        this.score = score;
        this.totalscore = totalscore;
        this.username = username;
        this.ID = ID;
        this.email = email;
        this.profilepic = profilepic;
        this.ppic1 = ppic1;
        this.ppic2 = ppic2;
        this.ppic3 = ppic3;
        this.ppic4 = ppic4;
        this.ppic5 = ppic5;
        this.ppic6 = ppic6;
        this.ppic7 = ppic7;
        this.ppic8 = ppic8;
        this.best_lvl1 = best_lvl1;
        this.best_lvl2 = best_lvl2;
        this.best_lvl3 = best_lvl3;
        this.best_lvl4 = best_lvl4;
        this.best_boss_battle= best_boss_battle;
        this.bestscore = bestscore;
    }

    public void setBest_boss_battle(float best_boss_battle) {
        this.best_boss_battle = best_boss_battle;
    }

    public float getBest_boss_battle() {
        return best_boss_battle;
    }

    public int getBestscore() {
        return bestscore;
    }

    public void setBestscore(int bestscore) {
        this.bestscore = bestscore;
    }

    public float getBest_lvl1() {
        return best_lvl1;
    }

    public void setBest_lvl1(float best_lvl1) {
        this.best_lvl1 = best_lvl1;
    }

    public float getBest_lvl2() {
        return best_lvl2;
    }

    public void setBest_lvl2(float best_lvl2) {
        this.best_lvl2 = best_lvl2;
    }

    public float getBest_lvl3() {
        return best_lvl3;
    }

    public void setBest_lvl3(float best_lvl3) {
        this.best_lvl3 = best_lvl3;
    }

    public float getBest_lvl4() {
        return best_lvl4;
    }

    public void setBest_lvl4(float best_lvl4) {
        this.best_lvl4 = best_lvl4;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getppic1() {
        return ppic1;
    }

    public void setppic1(boolean ppic1) {
        this.ppic1 = ppic1;
    }

    public int getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(int totalscore) {
        this.totalscore = totalscore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public boolean getppic2() {
        return ppic1;
    }

    public void setppic2(boolean ppic2) {
        this.ppic2 = ppic2;
    }

    public boolean getppic3() {
        return ppic1;
    }

    public void setppic3(boolean ppic3) {
        this.ppic3 = ppic3;
    }

    public boolean getppic4() {
        return ppic1;
    }

    public void setppic4(boolean ppic4) {
        this.ppic4 = ppic4;
    }

    public boolean getppic5() {
        return ppic1;
    }

    public void setppic5(boolean ppic5) {
        this.ppic5 = ppic5;
    }
    public boolean getppic6() {
        return ppic1;
    }

    public void setppic6(boolean ppic6) {
        this.ppic6 = ppic6;
    }
    public boolean getppic7() {
        return ppic1;
    }

    public void setppic7(boolean ppic7) {
        this.ppic7 = ppic7;
    }

    public boolean getppic8() {
        return ppic1;
    }

    public void setppic8(boolean ppic8) {
        this.ppic8 = ppic8;
    }
    public int getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(int profilepic) {this.profilepic = profilepic;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
