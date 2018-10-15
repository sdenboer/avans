package techpal.Models;

import java.time.LocalDate;

public class Lesson {

    private String stu, stuNm, per, prog, tstl, ttr, ttrNm, stuNiv, stuPc, stuHnr;
    private int isFin;
    private LocalDate dtm;

    public Lesson() {
    }

    public String getStu() {
          return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public String getStuNm() {
        return stuNm;
    }

    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    public String getPer() {
        return per;
    }

    public void setPer(String per) {
        this.per = per;
    }

    public String getProg() {
        return prog;
    }

    public void setProg(String prog) {
        this.prog = prog;
    }

    public String getTstl() {
        return tstl;
    }

    public void setTstl(String tstl) {
        this.tstl = tstl;
    }

    public String getTtr() {
        return ttr;
    }

    public void setTtr(String ttr) {
        this.ttr = ttr;
    }

    public String getTtrNm() {
        return ttrNm;
    }

    public void setTtrNm(String ttrNm) {
        this.ttrNm = ttrNm;
    }

    public int getIsFin() {
        return isFin;
    }

    public void setIsFin(int isFin) {
        this.isFin = isFin;
    }

    public LocalDate getDtm() {
        return dtm;
    }

    public void setDtm(LocalDate dtm) {
        this.dtm = dtm;
    }

    public String getStuNiv() {
        return stuNiv;
    }

    public void setStuNiv(String stuNiv) {
        this.stuNiv = stuNiv;
    }

    public String getStuPc() {
        return stuPc;
    }

    public void setStuPc(String stuPc) {
        this.stuPc = stuPc;
    }

    public String getStuHnr() {
        return stuHnr;
    }

    public void setStuHnr(String stuHnr) {
        this.stuHnr = stuHnr;
    }

}
