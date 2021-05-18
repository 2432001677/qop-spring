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
    public static final String QTYPE = "qtype";
    public static final String ANSWER = "answer";

    private BruceBsonUtils() {
    }

    public static Bson matchByQid(String qid) {
        return Aggregates.match(Filters.eq("questionnaire_id", qid));
    }

    public static Bson matchNe(Integer qtype) {
        return Aggregates.match(Filters.ne(QTYPE, qtype));
    }

    public static Bson matchEq(Integer qtype) {
        return Aggregates.match(Filters.in(QTYPE, qtype));
    }

    public static Bson matchIndex(Integer index) {
        return Aggregates.match(Filters.eq("index", index));
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

    public static Bson projectBlank() {
        return Aggregates.project(
                new Document("index", "$answered_questions.index")
                        .append("content", "$answered_questions.content")
                        .append("qtype", "$answered_questions.qtype")
        );
    }

    public static Bson unwindAnswer() {
        return Aggregates.unwind("$answered_questions");
    }

    public static Bson unwindOptions() {
        return Aggregates.unwind("$" + ANSWER);
    }

    public static Bson projectAnswer() {
        return Aggregates.project(
                new Document("index", "$answered_questions.index")
                        .append(QTYPE, "$answered_questions.qtype")
                        .append("pass", "$answered_questions.pass")
                        .append(ANSWER, "$answered_questions.answer")
                        .append("score", "$answered_questions.score")
        );
    }

    public static Bson projectFirstAnswer() {
        return Aggregates.project(
                new Document("_id", "$" + QTYPE)
                        .append(ANSWER, new Document("$arrayElemAt", Arrays.asList("$" + ANSWER, 0)))
        );
    }

    public static Bson groupAverage() {
        return Aggregates.group("$index", Accumulators.avg("average_score", "$score"));
    }

    public static Bson groupCount() {
        return Aggregates.group("$" + ANSWER, Accumulators.sum("count", 1));
    }

    public static List<Bson> getAvgScore(String qid) {
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

    public static List<Bson> getSingleOption(String qid, Integer index, Integer qtype) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectAnswer(),
                matchEq(qtype),
                matchIndex(index),
                projectFirstAnswer(),
                groupCount()
        );
    }

    public static List<Bson> getMultiOption(String qid, Integer index) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectAnswer(),
                matchEq(QuestionType.MULTIPLE_SELECT.getCode()),
                matchIndex(index),
                unwindOptions(),
                groupCount()
        );
    }

    public static List<Bson> getBlankAnswer(String qid, Integer index) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectBlank(),
                matchIndex(index)
        );
    }
}
