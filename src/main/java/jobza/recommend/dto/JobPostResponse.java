package jobza.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobPostResponse {
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
    static class CorpInfo {
        private String corpNm;
        private String indTpCdNm;
        private String busiSize;
        private String yrSalesAmt;
        private String totPsncnt;
    }
    @Getter @Setter
    static class WantedInfo {
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
        private List<String> compAbl;
        private List<String> pfCond;
        private List<String> etcPfCond;
        private String srchKeywordNm;
        private String dtlRecrContUrl;
        private String jobsCd;
        private String minEdubgIcd;
        private String maxEdubgIcd;
        private String empTpCd;
        private String enterTpCd;
        private String salTpCd;

        // Getters and setters
    }
    @Getter @Setter
    static class SelMthdInfo {
        private String receiptCloseDt;
        private String selMthd;
        private String rcptMthd;
        private List<String> submitDoc;
        private String etcHopeCont;
        private String workRegion;

        // Getters and setters
    }
    @Getter @Setter
    static class WorkInfo {
        private String salTpNm;
        private String workdayWorkhrCont;
        private List<String> fourIns;
        private String retirepay;
        private List<String> etcWelfare;
        private String disableCvntl;

        // Getters and setters
    }
    @Getter @Setter
    static class LocationInfo {
        private String staAreaRegionCd;
        private String lineCd;
        private String staNmCd;
        private String exitNoCd;
        private String walkDistCd;
        private String nearLine;

    }
    @Getter @Setter
    static class EmpChargerInfo {
        private String contactTelno;
        private String empChargerDpt;
    }

}

