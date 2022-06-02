package br.com.zup.edu.nutricionistas.controller;

import br.com.zup.edu.nutricionistas.model.Nutricionista;
import br.com.zup.edu.nutricionistas.repository.NutricionistaRepository;
import br.com.zup.edu.nutricionistas.util.MensagemDeErro;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
class CadastrarNutricionistaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private NutricionistaRepository repository;

    @BeforeEach
    void setUp(){
        this.repository.deleteAll();
    }

    @Test
    @DisplayName("Deve cadastrar uma nutricionista com dados validos")
    void test1() throws Exception{
        //cenario
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest("bruna","bruna@gmail.com","CRN5203PI","730.561.950-71", LocalDate.of(1991, 9, 05));
        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload);

        //acao e corretude
        mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isCreated()
                )
                .andExpect(
                        MockMvcResultMatchers.redirectedUrlPattern("http://localhost/nutricionistas/*")
                );

        List<Nutricionista> nutricionistas = repository.findAll();
        assertEquals(1,nutricionistas.size());
    }


    @Test
    @DisplayName("Não deve cadastrar uma nutricionista com invalidos")
    void test2() throws Exception{
        //cenario
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest(null,null,null,null, null);
        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(5,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo CRN não deve estar em branco",
                "O campo email não deve estar em branco",
                "O campo dataNascimento não deve ser nulo",
                "O campo cpf não deve estar em branco",
                "O campo nome não deve estar em branco"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar uma nutricionista com email invalido")
    void test3() throws Exception{
        //cenario
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest("bruna","brunagmail.com","CRN5203PI","730.561.950-71", LocalDate.of(1991, 9, 05));
        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo email deve ser um endereço de e-mail bem formado"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar uma nutricionista com cpf invalido")
    void test4() throws Exception{
        //cenario
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest("bruna","bruna@gmail.com","CRN5203PI","730.561.950-71", LocalDate.of(1991, 9, 05));
        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo cpf número do registro de contribuinte individual brasileiro (CPF) inválido"
        ));
    }

    @Test
    @DisplayName("Não deve cadastrar uma nutricionista com data de nascimento no presente ou futuro")
    void test5() throws Exception{
        //cenario
        NutricionistaRequest nutricionistaRequest = new NutricionistaRequest("bruna","bruna@gmail.com","CRN5203PI","730.561.950-71", LocalDate.now());
        String payload = mapper.writeValueAsString(nutricionistaRequest);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/nutricionistas")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Accept-Language","pt-br")
                .content(payload);

        //acao e corretude
        String payloadResponse = mockMvc.perform(request)
                .andExpect(
                        MockMvcResultMatchers.status().isBadRequest()
                )
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        MensagemDeErro mensagemDeErro = mapper.readValue(payloadResponse, MensagemDeErro.class);

        assertEquals(1,mensagemDeErro.getMensagens().size());
        assertThat(mensagemDeErro.getMensagens(), containsInAnyOrder(
                "O campo dataNascimento deve ser uma data passada"
        ));
    }

}