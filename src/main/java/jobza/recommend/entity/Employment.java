package jobza.recommend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Employment {

    @Id
    private String id;

    private String occupation1;
    private String occupation2;
    private String occupation3;
    private String regionCd;
    private String wantedAuthNo;

    private CorpInfo corpInfo;
    private WantedInfo wantedInfo;
    private SelMthdInfo selMthdInfo;
    private WorkInfo workInfo;
    private LocationInfo locationInfo;
    private EmpChargerInfo empChargerInfo;

    @Getter @Setter
    public static class CorpInfo {
        private String corpNm;
        private String indTpCdNm;
        private String busiSize;
        private String yrSalesAmt;
        private String totPsncnt;
    }

    @Getter @Setter
    public static class WantedInfo {
        private String jobsNm;
        private String wantedTitle;
        private String relJobsNm;
        private String jobCont;
        private String empTpNm;
        private String collectPsncnt;
        private String enterTpNm;
        private String eduNm;
        private String major;
        private String certificate;
        private String[] compAbl;
        private String[] pfCond;
        private String[] etcPfCond;
        private String srchKeywordNm;
        private String dtlRecrContUrl;
        private String jobsCd;
        private String minEdubgIcd;
        private String maxEdubgIcd;
        private String empTpCd;
        private String enterTpCd;
        private String salTpCd;
    }

    @Getter @Setter
    public static class SelMthdInfo {
        private String receiptCloseDt;
        private String selMthd;
        private String rcptMthd;
        private String[] submitDoc;
        private String etcHopeCont;
        private String workRegion;
    }

    @Getter @Setter
    public static class WorkInfo {
        private String salTpNm;
        private String workdayWorkhrCont;
        private String[] fourIns;
        private String retirepay;
        private String[] etcWelfare;
        private String disableCvntl;
    }

    @Getter @Setter
    public static class LocationInfo {
        private String staAreaRegionCd;
        private String lineCd;
        private String staNmCd;
        private String exitNoCd;
        private String walkDistCd;
        private String nearLine;
    }

    @Getter @Setter
    public static class EmpChargerInfo {
        private String contactTelno;
        private String empChargerDpt;
    }
}

