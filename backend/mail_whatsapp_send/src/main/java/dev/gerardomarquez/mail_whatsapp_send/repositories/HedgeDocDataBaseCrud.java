package dev.gerardomarquez.mail_whatsapp_send.repositories;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.gerardomarquez.mail_whatsapp_send.dtos.NoteEnabled;
import dev.gerardomarquez.mail_whatsapp_send.dtos.responses.NoteInfoResponse;

/**
 * Repositorio para interactuar con la base de datos de HedgeDoc.
 */
@Repository
public class HedgeDocDataBaseCrud {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final Logger log = LoggerFactory.getLogger(HedgeDocDataBaseCrud.class);

    public HedgeDocDataBaseCrud(@Qualifier("secondaryJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Obtiene el permiso de una nota en HedgeDoc a partir de su shortid.
     * @param notesIds Lista de shortids de las notas a consultar.
     * @return Lista de notas con el shortid de la nota y un booleano indicando si está protegida o no.
     */
    public List<NoteEnabled> getNotePermission(List<String> notesIds) {
        List<NoteEnabled> notes = new ArrayList<>();
        String sql = "SELECT shortid, permission FROM \"Notes\" WHERE shortid in (:notesIds)";
        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("notesIds", notesIds);
        try {
            notes = jdbcTemplate.query(
                sql,
                parameters,
                (rs, rowNum) -> {
                    return new NoteEnabled(
                        rs.getString("shortid"),
                        ("freely".equals(rs.getString("permission") ) || "editable".equals(rs.getString("permission") ) )
                    );
                }
            );

            return notes;
        } catch (Exception e) {
            log.error("Error al obtener el permiso de la nota: " + e.getMessage(), e);
            e.printStackTrace();
        }
        return notes;
    }

    /**
     * Elimina permanentemente la nota que desea.
     * @param id shortid de la nota que se desea eliminar.
     */
    public void deleteOneNote(String id) {
        String sql = "delete from \"Notes\" where shortid = :id";

        MapSqlParameterSource parameters = new MapSqlParameterSource().addValue("id", id);
        Integer rowsAffected = 0;

        try {
            rowsAffected = jdbcTemplate.update(sql, parameters);

            if(rowsAffected != 1){
                log.error("Hubo un fallo en el borrado");
                throw new RuntimeException("Hubo un fallo en el borrado");
            }
        } catch (Exception e) {
            log.error("Error al obtener el permiso de la nota: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public String getNoteId(NoteInfoResponse infoNote) {
        String sql = "SELECT shortid FROM \"Notes\" WHERE \"createdAt\" = CAST(:time AS timestamptz) and title = :title";
        MapSqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("time", infoNote.createtime() )
            .addValue("title", infoNote.title() );

        String id = new String();

        try {
            id = jdbcTemplate.queryForObject(
                sql,
                parameters,
                String.class
            );
        } catch (Exception e) {
            log.error("Error al obtener el shortid de la nota: " + e.getMessage(), e);
            e.printStackTrace();
        }
        
        return id;
    }
}
