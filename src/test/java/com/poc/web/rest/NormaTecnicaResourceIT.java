package com.poc.web.rest;

import com.poc.PocApp;
import com.poc.domain.NormaTecnica;
import com.poc.repository.NormaTecnicaRepository;
import com.poc.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.poc.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link NormaTecnicaResource} REST controller.
 */
@SpringBootTest(classes = PocApp.class)
public class NormaTecnicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDUSTRIAL = false;
    private static final Boolean UPDATED_INDUSTRIAL = true;

    private static final String DEFAULT_SETOR = "AAAAAAAAAA";
    private static final String UPDATED_SETOR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NormaTecnicaRepository normaTecnicaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restNormaTecnicaMockMvc;

    private NormaTecnica normaTecnica;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NormaTecnicaResource normaTecnicaResource = new NormaTecnicaResource(normaTecnicaRepository);
        this.restNormaTecnicaMockMvc = MockMvcBuilders.standaloneSetup(normaTecnicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NormaTecnica createEntity(EntityManager em) {
        NormaTecnica normaTecnica = new NormaTecnica()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .industrial(DEFAULT_INDUSTRIAL)
            .setor(DEFAULT_SETOR)
            .dataCriacao(DEFAULT_DATA_CRIACAO);
        return normaTecnica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NormaTecnica createUpdatedEntity(EntityManager em) {
        NormaTecnica normaTecnica = new NormaTecnica()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .industrial(UPDATED_INDUSTRIAL)
            .setor(UPDATED_SETOR)
            .dataCriacao(UPDATED_DATA_CRIACAO);
        return normaTecnica;
    }

    @BeforeEach
    public void initTest() {
        normaTecnica = createEntity(em);
    }

    @Test
    @Transactional
    public void createNormaTecnica() throws Exception {
        int databaseSizeBeforeCreate = normaTecnicaRepository.findAll().size();

        // Create the NormaTecnica
        restNormaTecnicaMockMvc.perform(post("/api/norma-tecnicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaTecnica)))
            .andExpect(status().isCreated());

        // Validate the NormaTecnica in the database
        List<NormaTecnica> normaTecnicaList = normaTecnicaRepository.findAll();
        assertThat(normaTecnicaList).hasSize(databaseSizeBeforeCreate + 1);
        NormaTecnica testNormaTecnica = normaTecnicaList.get(normaTecnicaList.size() - 1);
        assertThat(testNormaTecnica.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testNormaTecnica.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testNormaTecnica.isIndustrial()).isEqualTo(DEFAULT_INDUSTRIAL);
        assertThat(testNormaTecnica.getSetor()).isEqualTo(DEFAULT_SETOR);
        assertThat(testNormaTecnica.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void createNormaTecnicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = normaTecnicaRepository.findAll().size();

        // Create the NormaTecnica with an existing ID
        normaTecnica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNormaTecnicaMockMvc.perform(post("/api/norma-tecnicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaTecnica)))
            .andExpect(status().isBadRequest());

        // Validate the NormaTecnica in the database
        List<NormaTecnica> normaTecnicaList = normaTecnicaRepository.findAll();
        assertThat(normaTecnicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNormaTecnicas() throws Exception {
        // Initialize the database
        normaTecnicaRepository.saveAndFlush(normaTecnica);

        // Get all the normaTecnicaList
        restNormaTecnicaMockMvc.perform(get("/api/norma-tecnicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(normaTecnica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].industrial").value(hasItem(DEFAULT_INDUSTRIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].setor").value(hasItem(DEFAULT_SETOR.toString())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getNormaTecnica() throws Exception {
        // Initialize the database
        normaTecnicaRepository.saveAndFlush(normaTecnica);

        // Get the normaTecnica
        restNormaTecnicaMockMvc.perform(get("/api/norma-tecnicas/{id}", normaTecnica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(normaTecnica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.industrial").value(DEFAULT_INDUSTRIAL.booleanValue()))
            .andExpect(jsonPath("$.setor").value(DEFAULT_SETOR.toString()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNormaTecnica() throws Exception {
        // Get the normaTecnica
        restNormaTecnicaMockMvc.perform(get("/api/norma-tecnicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNormaTecnica() throws Exception {
        // Initialize the database
        normaTecnicaRepository.saveAndFlush(normaTecnica);

        int databaseSizeBeforeUpdate = normaTecnicaRepository.findAll().size();

        // Update the normaTecnica
        NormaTecnica updatedNormaTecnica = normaTecnicaRepository.findById(normaTecnica.getId()).get();
        // Disconnect from session so that the updates on updatedNormaTecnica are not directly saved in db
        em.detach(updatedNormaTecnica);
        updatedNormaTecnica
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .industrial(UPDATED_INDUSTRIAL)
            .setor(UPDATED_SETOR)
            .dataCriacao(UPDATED_DATA_CRIACAO);

        restNormaTecnicaMockMvc.perform(put("/api/norma-tecnicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNormaTecnica)))
            .andExpect(status().isOk());

        // Validate the NormaTecnica in the database
        List<NormaTecnica> normaTecnicaList = normaTecnicaRepository.findAll();
        assertThat(normaTecnicaList).hasSize(databaseSizeBeforeUpdate);
        NormaTecnica testNormaTecnica = normaTecnicaList.get(normaTecnicaList.size() - 1);
        assertThat(testNormaTecnica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testNormaTecnica.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testNormaTecnica.isIndustrial()).isEqualTo(UPDATED_INDUSTRIAL);
        assertThat(testNormaTecnica.getSetor()).isEqualTo(UPDATED_SETOR);
        assertThat(testNormaTecnica.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingNormaTecnica() throws Exception {
        int databaseSizeBeforeUpdate = normaTecnicaRepository.findAll().size();

        // Create the NormaTecnica

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNormaTecnicaMockMvc.perform(put("/api/norma-tecnicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(normaTecnica)))
            .andExpect(status().isBadRequest());

        // Validate the NormaTecnica in the database
        List<NormaTecnica> normaTecnicaList = normaTecnicaRepository.findAll();
        assertThat(normaTecnicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNormaTecnica() throws Exception {
        // Initialize the database
        normaTecnicaRepository.saveAndFlush(normaTecnica);

        int databaseSizeBeforeDelete = normaTecnicaRepository.findAll().size();

        // Delete the normaTecnica
        restNormaTecnicaMockMvc.perform(delete("/api/norma-tecnicas/{id}", normaTecnica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NormaTecnica> normaTecnicaList = normaTecnicaRepository.findAll();
        assertThat(normaTecnicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NormaTecnica.class);
        NormaTecnica normaTecnica1 = new NormaTecnica();
        normaTecnica1.setId(1L);
        NormaTecnica normaTecnica2 = new NormaTecnica();
        normaTecnica2.setId(normaTecnica1.getId());
        assertThat(normaTecnica1).isEqualTo(normaTecnica2);
        normaTecnica2.setId(2L);
        assertThat(normaTecnica1).isNotEqualTo(normaTecnica2);
        normaTecnica1.setId(null);
        assertThat(normaTecnica1).isNotEqualTo(normaTecnica2);
    }
}
