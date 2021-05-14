package cn.edu.zucc.utils;

import cn.edu.zucc.enums.QuestionType;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.util.BsonUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bruce
 * @since 05-14-2021
 **/
public class BruceBsonUtils extends BsonUtils {
    private BruceBsonUtils() {

    }

    public static Bson matchByQid(String qid) {
        return Aggregates.match(Filters.eq("questionnaire_id", qid));
    }

    public static Bson matchNe(Integer qtype) {
        return Aggregates.match(Filters.ne("qtype", qtype));
    }

    public static Bson matchByGroupId(Long groupId) {
        return Aggregates.match(Filters.eq("group_id", groupId));
    }

    public static Bson projectQid() {
        return Aggregates.project(
                new Document("_id", "$questionnaire_id")
                        .append("answered_questions", "$answered_questions")
        );
    }

    public static Bson unwindAnswer() {
        return Aggregates.unwind("$answered_questions");
    }

    public static Bson projectAnswer() {
        return Aggregates.project(
                new Document("index", "$answered_questions.index")
                        .append("qtype", "$answered_questions.qtype")
                        .append("pass", "$answered_questions.pass")
                        .append("answer", "$answered_questions.answer")
                        .append("score", "$answered_questions.score")
        );
    }

    public static Bson groupAverage() {
        return Aggregates.group("$index", Accumulators.avg("average_score", "$score"));
    }

    public static List<Bson> getBson(String qid) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectAnswer(),
                matchNe(QuestionType.BLANK.getCode()),
                matchNe(QuestionType.RATES.getCode()),
                matchNe(QuestionType.WEIGHT_ASSIGN.getCode()),
                matchNe(QuestionType.UPLOAD_FILE.getCode()),
                groupAverage()
        );
    }
}
