package pl.mbab.subjectdeclaration.model.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.mbab.subdeclaration.model.subject.FieldSubject;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "field")
    private List<FieldSubject> fieldSubjects = new ArrayList<>();

//    public void loadLists(){
//        String[] advAnalyticsMajor = new String[] {"22347", "22309", "22306", "22439", "22312",
//                "22208", "22211", "22305", "22280", "22311", "22310", "22348", "22349", "22616",
//                "22166"};
//        for(String s : advAnalyticsMajor) {
//            majorSubjects.add(s);
//        }
//
//        String[] advAnalyticsRelated = new String[] {"22200", "23780", "23307", "23831", "23020",
//                "23318", "22299", "22295", "22205", "22206", "23024", "23920", "23497", "23681",
//                "22034", "23490", "22258", "23122", "23922", "22057", "23385", "22058", "23145",
//                "22062", "23635", "23013", "23541", "23300", "23405", "23643", "23406", "23419"};
//
//        for(String s : advAnalyticsMajor) {
//            relatedSubjects.add(s);
//        }
//    }

//    ADVANCED_ANALYTICS("Analiza danych – big data"),
//    E_BUSINESS("E-biznes"),
//    ECONOMICS("Ekonomia"),
//    FINANCE_AND_ACCOUNTING("Finanse i rachunkowość"),
//    GLOBAL_BUSINESS("Globalny biznes, finanse i zarządzanie (Governance)"),
//    QUANTITATIVE("Metody ilościowe w ekonomii i systemy informacyjne"),
//    INTERNATIONAL_BUSINESS("międzynarodowe stosunki gospodarcze"),
//    MANAGEMENT("Zarządzanie");
}
