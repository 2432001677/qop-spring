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
    public static final String INDEX = "index";
    public static final String ANSWERED_QUESTIONS = "answered_questions";

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
        return Aggregates.match(Filters.eq(INDEX, index));
    }

    public static Bson matchByGroupId(Long groupId) {
        return Aggregates.match(Filters.eq("group_id", groupId));
    }

    public static Bson projectQid() {
        return Aggregates.project(
                new Document("_id", "$questionnaire_id")
                        .append(ANSWERED_QUESTIONS, "$" + ANSWERED_QUESTIONS)
        );
    }

    public static Bson projectBlank() {
        return Aggregates.project(
                new Document(INDEX, "$" + ANSWERED_QUESTIONS + "." + INDEX)
                        .append("content", "$" + ANSWERED_QUESTIONS + ".content")
                        .append(QTYPE, "$" + ANSWERED_QUESTIONS + "." + QTYPE)
        );
    }

    public static Bson projectWeight() {
        return Aggregates.project(
                new Document(INDEX, "$" + ANSWERED_QUESTIONS + "." + INDEX)
                        .append(ANSWER, "$" + ANSWERED_QUESTIONS + "." + ANSWER)
                        .append(QTYPE, "$" + ANSWERED_QUESTIONS + "." + QTYPE)
        );
    }

    public static Bson unwindAnswer() {
        return Aggregates.unwind("$" + ANSWERED_QUESTIONS);
    }

    public static Bson unwindOptions() {
        return Aggregates.unwind("$" + ANSWER);
    }

    public static Bson projectAnswer() {
        return Aggregates.project(
                new Document(INDEX, "$" + ANSWERED_QUESTIONS + "." + INDEX)
                        .append(QTYPE, "$" + ANSWERED_QUESTIONS + "." + QTYPE)
                        .append("pass", "$" + ANSWERED_QUESTIONS + ".pass")
                        .append(ANSWER, "$" + ANSWERED_QUESTIONS + "." + ANSWER)
                        .append("score", "$" + ANSWERED_QUESTIONS + ".score")
        );
    }

    public static Bson projectFirstAnswer() {
        return Aggregates.project(
                new Document("_id", "$" + QTYPE)
                        .append(ANSWER, new Document("$arrayElemAt", Arrays.asList("$" + ANSWER, 0)))
        );
    }

    public static Bson projectOptionIndex(Integer optionIndex) {
        return Aggregates.project(
                new Document(ANSWER, new Document("$arrayElemAt", Arrays.asList("$" + ANSWER, optionIndex)))
        );
    }

    public static Bson projectWeightScore() {
        return Aggregates.project(
                new Document("score", "$" + ANSWER + ".score")
                        .append("weight", "$" + ANSWER + ".weight")
        );
    }

    public static Bson groupAverage() {
        return Aggregates.group("$" + INDEX, Accumulators.avg("average_score", "$score"));
    }

    public static Bson groupCount() {
        return Aggregates.group("$" + ANSWER, Accumulators.sum("count", 1));
    }

    public static Bson groupIndexCount() {
        return Aggregates.group("$" + INDEX, Accumulators.sum("count", 1));
    }

    public static Bson groupSumScore() {
        return Aggregates.group("$_id", Accumulators.sum("sum_score", "$score"), Accumulators.sum("sum_weight", "$weight"));
    }

    public static List<Bson> groupAnswerCount(String qid, Integer index) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectAnswer(),
                matchIndex(index),
                groupIndexCount()
        );
    }

    public static List<Bson> getAvgScore(String qid) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectAnswer(),
                matchNe(QuestionType.BLANK.getCode()),
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

    public static List<Bson> getWeightAnswer(String qid, Integer index, Integer optionIndex) {
        return Arrays.asList(
                matchByQid(qid),
                projectQid(),
                unwindAnswer(),
                projectWeight(),
                matchIndex(index),
                projectOptionIndex(optionIndex),
                projectWeightScore(),
                groupSumScore()
        );
    }
}
