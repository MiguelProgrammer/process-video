///*
// * Copyright (c) 2024-2025. MiguelProgrammer
// */
//
//package br.com.techchallenge.fiap.process.framework.tools.cucumber;
//
//
//import io.cucumber.java.it.Quando;
//import io.cucumber.java.pt.Então;
//import io.restassured.response.Response;
//import org.junit.platform.suite.api.IncludeEngines;
//import org.junit.platform.suite.api.SelectClasspathResource;
//import org.junit.platform.suite.api.Suite;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.MediaType;
//
//import static io.restassured.RestAssured.given;
//
//@Suite
//@IncludeEngines("cucumber")
//@SelectClasspathResource("features")
//public class CucumberTest {
//
//    private static final Logger log = LoggerFactory.getLogger(CucumberTest.class);
//    private Response response;
//    private final String REST_API_ACOMPANHEMENTO = "http://localhost:86/neighborfood/acompanhamento/";
//
//    @Quando("consulta status do pedido")
//    public AcompanhamentoResponseDTO consulta_status_do_pedido() {
//
//        Long idPedido = 1L;
//        try {
//            response = given().
//                    contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .param("idPedido", idPedido)
//                    .when()
//                    .get(REST_API_ACOMPANHEMENTO).prettyPeek();
//            return response.then().extract().as(AcompanhamentoResponseDTO.class);
//        } catch (Exception e) {
//
//        }
//        return null;
//    }
//
//    @Então("atualiza status do pedido")
//    public AcompanhamentoResponseDTO atualiza_status_do_pedido() {
//
//        AcompanhamentoResponseDTO dto = consulta_status_do_pedido();
//        try {
//            response = given().
//                    contentType(MediaType.APPLICATION_JSON_VALUE)
//                    .body(dto.getPedido().getId())
//                    .when()
//                    .post(REST_API_ACOMPANHEMENTO + "/" + dto.getPedido()).prettyPeek();
//
//            return response.then().extract().as(AcompanhamentoResponseDTO.class);
//        } catch (Exception e) {
//
//        }
//
//        return null;
//    }
//
//    @Então("recebe uma mensagem sobre andamento do pedido e atendimento")
//    public String recebe_uma_mensagem_sobre_andamento_do_pedido_e_atendimento() {
//        try {
//            return atualiza_status_do_pedido().toString();
//        } catch (Exception e) {
//
//        }
//        return null;
//    }
//
//}
