package org.danit.model.dao;

import com.jcabi.jdbc.JdbcSession;
import org.danit.model.Source;
import org.danit.model.dto.Answer;
import org.danit.model.outcome.OutcomeAnswer;
import org.danit.model.outcome.OutcomeAnswerList;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOPgAnswer implements DAO<Answer>{
    private final DataSource source;

    public DAOPgAnswer(Source conn) {
        this.source = conn.source();
    }

    @Override
    public Answer get(int pk) {
        try {
            return new JdbcSession(source)
                    .sql("SELECT * FROM answer WHERE a_id = ?")
                    .set(pk)
                    .select(new OutcomeAnswer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Answer();
    }

    @Override
    public Answer insert(Answer e) {
        try {
            return new JdbcSession(source)
                    .sql("INSERT INTO answer (a_question_id, a_text, a_type) VALUES (?, ?, ?)")
                    .set(e.getQuestionId())
                    .set(e.getText())
                    .set(e.getType())
                    .insert(new OutcomeAnswer());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return new Answer();
    }

    @Override
    public Answer update(Answer e)  {
        try {
            return new JdbcSession(source)
                    .sql("UPDATE answer SET a_question_id=?, a_text=?, a_type=? WHERE a_id = ?")
                    .set(e.getQuestionId())
                    .set(e.getText())
                    .set(e.getType())
                    .set(e.getId())
                    .update(new OutcomeAnswer());
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return new Answer();
    }

    @Override
    public void delete(int pk) {
        try {
            new JdbcSession(source)
                    .sql("DELETE FROM answer WHERE a_id = ?")
                    .set(pk)
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Answer> all() {
        return new ArrayList<>();
    }

    public List<Answer> getAllByQuestion(int id) {
        try {
            return new JdbcSession(source)
                    .sql("SELECT * FROM answer WHERE a_question_id = ?")
                    .set(id)
                    .select(new OutcomeAnswerList());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void markRightAnswer(int aw_pk) {
        try {
            new JdbcSession(source)
                    .sql("UPDATE answer SET a_type=1 WHERE a_id = ?")
                    .set(aw_pk)
                    .execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}