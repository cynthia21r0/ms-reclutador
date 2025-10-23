package com.ms_reclutador.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.util.List;

@Schema(description = "DTO para la respuesta de la API de DIPOMEX")
public class DipomexResponseDTO {

    private boolean error;
    private String message;

    @JsonProperty("codigo_postal")
    private CodigoPostalData codigoPostal;

    // Getters y Setters
    public boolean isError() { return error; }
    public void setError(boolean error) { this.error = error; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public CodigoPostalData getCodigoPostal() { return codigoPostal; }
    public void setCodigoPostal(CodigoPostalData codigoPostal) { this.codigoPostal = codigoPostal; }

    @Schema(description = "Datos del Código Postal")
    public static class CodigoPostalData {

        @JsonProperty("estado_id")
        private String estadoId;

        @JsonProperty("municipio_id")
        private String municipioId;

        private String estado;

        @JsonProperty("estado_abreviatura")
        private String estadoAbreviatura;

        private String municipio;

        @JsonProperty("centro_reparto")
        private String centroReparto;

        @JsonProperty("codigo_postal")
        private String codigoPostal;

        private List<ColoniaData> colonias;

        // Getters y Setters
        public String getEstadoId() { return estadoId; }
        public void setEstadoId(String estadoId) { this.estadoId = estadoId; }
        public String getMunicipioId() { return municipioId; }
        public void setMunicipioId(String municipioId) { this.municipioId = municipioId; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
        public String getEstadoAbreviatura() { return estadoAbreviatura; }
        public void setEstadoAbreviatura(String estadoAbreviatura) { this.estadoAbreviatura = estadoAbreviatura; }
        public String getMunicipio() { return municipio; }
        public void setMunicipio(String municipio) { this.municipio = municipio; }
        public String getCentroReparto() { return centroReparto; }
        public void setCentroReparto(String centroReparto) { this.centroReparto = centroReparto; }
        public String getCodigoPostal() { return codigoPostal; }
        public void setCodigoPostal(String codigoPostal) { this.codigoPostal = codigoPostal; }
        public List<ColoniaData> getColonias() { return colonias; }
        public void setColonias(List<ColoniaData> colonias) { this.colonias = colonias; }
    }

    @Schema(description = "Datos de las Colonias asociadas al CP")
    @JsonDeserialize(using = ColoniaDataDeserializer.class)
    public static class ColoniaData {

        @JsonProperty("colonia_id")
        private String coloniaId;

        private String colonia;

        @JsonProperty("TIPO_ASENTAMIENTO")
        private String tipoAsentamiento;

        // Constructor por defecto
        public ColoniaData() {}

        // Constructor completo
        public ColoniaData(String colonia) {
            this.colonia = colonia;
        }

        // Getters y Setters
        public String getColoniaId() { return coloniaId; }
        public void setColoniaId(String coloniaId) { this.coloniaId = coloniaId; }
        public String getColonia() { return colonia; }
        public void setColonia(String colonia) { this.colonia = colonia; }
        public String getTipoAsentamiento() { return tipoAsentamiento; }
        public void setTipoAsentamiento(String tipoAsentamiento) { this.tipoAsentamiento = tipoAsentamiento; }
    }

    // Deserializador personalizado para manejar tanto Strings como Objetos
    public static class ColoniaDataDeserializer extends JsonDeserializer<ColoniaData> {
        @Override
        public ColoniaData deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            ColoniaData coloniaData = new ColoniaData();

            // Si es un string simple
            if (p.getCurrentToken().isScalarValue()) {
                coloniaData.setColonia(p.getValueAsString());
                return coloniaData;
            }

            // Si es un objeto completo
            while (p.nextToken() != com.fasterxml.jackson.core.JsonToken.END_OBJECT) {
                String fieldName = p.getCurrentName();
                p.nextToken();

                switch (fieldName) {
                    case "colonia_id":
                        coloniaData.setColoniaId(p.getValueAsString());
                        break;
                    case "colonia":
                        coloniaData.setColonia(p.getValueAsString());
                        break;
                    case "TIPO_ASENTAMIENTO":
                        coloniaData.setTipoAsentamiento(p.getValueAsString());
                        break;
                }
            }

            return coloniaData;
        }
    }
}