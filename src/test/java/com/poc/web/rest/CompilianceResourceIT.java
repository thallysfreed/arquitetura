package com.poc.web.rest;

import com.poc.PocApp;
import com.poc.domain.Compiliance;
import com.poc.repository.CompilianceRepository;
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
 * Integration tests for the {@Link CompilianceResource} REST controller.
 */
@SpringBootTest(classes = PocApp.class)
public class CompilianceResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_SETOR = "AAAAAAAAAA";
    private static final String UPDATED_SETOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_INDUSTRIAL = false;
    private static final Boolean UPDATED_INDUSTRIAL = true;

    private static final LocalDate DEFAULT_DATA_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CompilianceRepository compilianceRepository;

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

    private MockMvc restCompilianceMockMvc;

    private Compiliance compiliance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompilianceResource compilianceResource = new CompilianceResource(compilianceRepository);
        this.restCompilianceMockMvc = MockMvcBuilders.standaloneSetup(compilianceResource)
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
    public static Compiliance createEntity(EntityManager em) {
        Compiliance compiliance = new Compiliance()
            .nome(DEFAULT_NOME)
            .descricao(DEFAULT_DESCRICAO)
            .setor(DEFAULT_SETOR)
            .industrial(DEFAULT_INDUSTRIAL)
            .dataCriacao(DEFAULT_DATA_CRIACAO);
        return compiliance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Compiliance createUpdatedEntity(EntityManager em) {
        Compiliance compiliance = new Compiliance()
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .setor(UPDATED_SETOR)
            .industrial(UPDATED_INDUSTRIAL)
            .dataCriacao(UPDATED_DATA_CRIACAO);
        return compiliance;
    }

    @BeforeEach
    public void initTest() {
        compiliance = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompiliance() throws Exception {
        int databaseSizeBeforeCreate = compilianceRepository.findAll().size();

        // Create the Compiliance
        restCompilianceMockMvc.perform(post("/api/compiliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compiliance)))
            .andExpect(status().isCreated());

        // Validate the Compiliance in the database
        List<Compiliance> compilianceList = compilianceRepository.findAll();
        assertThat(compilianceList).hasSize(databaseSizeBeforeCreate + 1);
        Compiliance testCompiliance = compilianceList.get(compilianceList.size() - 1);
        assertThat(testCompiliance.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCompiliance.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testCompiliance.getSetor()).isEqualTo(DEFAULT_SETOR);
        assertThat(testCompiliance.isIndustrial()).isEqualTo(DEFAULT_INDUSTRIAL);
        assertThat(testCompiliance.getDataCriacao()).isEqualTo(DEFAULT_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void createCompilianceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = compilianceRepository.findAll().size();

        // Create the Compiliance with an existing ID
        compiliance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompilianceMockMvc.perform(post("/api/compiliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compiliance)))
            .andExpect(status().isBadRequest());

        // Validate the Compiliance in the database
        List<Compiliance> compilianceList = compilianceRepository.findAll();
        assertThat(compilianceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompiliances() throws Exception {
        // Initialize the database
        compilianceRepository.saveAndFlush(compiliance);

        // Get all the compilianceList
        restCompilianceMockMvc.perform(get("/api/compiliances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(compiliance.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO.toString())))
            .andExpect(jsonPath("$.[*].setor").value(hasItem(DEFAULT_SETOR.toString())))
            .andExpect(jsonPath("$.[*].industrial").value(hasItem(DEFAULT_INDUSTRIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dataCriacao").value(hasItem(DEFAULT_DATA_CRIACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCompiliance() throws Exception {
        // Initialize the database
        compilianceRepository.saveAndFlush(compiliance);

        // Get the compiliance
        restCompilianceMockMvc.perform(get("/api/compiliances/{id}", compiliance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(compiliance.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO.toString()))
            .andExpect(jsonPath("$.setor").value(DEFAULT_SETOR.toString()))
            .andExpect(jsonPath("$.industrial").value(DEFAULT_INDUSTRIAL.booleanValue()))
            .andExpect(jsonPath("$.dataCriacao").value(DEFAULT_DATA_CRIACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompiliance() throws Exception {
        // Get the compiliance
        restCompilianceMockMvc.perform(get("/api/compiliances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompiliance() throws Exception {
        // Initialize the database
        compilianceRepository.saveAndFlush(compiliance);

        int databaseSizeBeforeUpdate = compilianceRepository.findAll().size();

        // Update the compiliance
        Compiliance updatedCompiliance = compilianceRepository.findById(compiliance.getId()).get();
        // Disconnect from session so that the updates on updatedCompiliance are not directly saved in db
        em.detach(updatedCompiliance);
        updatedCompiliance
            .nome(UPDATED_NOME)
            .descricao(UPDATED_DESCRICAO)
            .setor(UPDATED_SETOR)
            .industrial(UPDATED_INDUSTRIAL)
            .dataCriacao(UPDATED_DATA_CRIACAO);

        restCompilianceMockMvc.perform(put("/api/compiliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompiliance)))
            .andExpect(status().isOk());

        // Validate the Compiliance in the database
        List<Compiliance> compilianceList = compilianceRepository.findAll();
        assertThat(compilianceList).hasSize(databaseSizeBeforeUpdate);
        Compiliance testCompiliance = compilianceList.get(compilianceList.size() - 1);
        assertThat(testCompiliance.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCompiliance.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testCompiliance.getSetor()).isEqualTo(UPDATED_SETOR);
        assertThat(testCompiliance.isIndustrial()).isEqualTo(UPDATED_INDUSTRIAL);
        assertThat(testCompiliance.getDataCriacao()).isEqualTo(UPDATED_DATA_CRIACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCompiliance() throws Exception {
        int databaseSizeBeforeUpdate = compilianceRepository.findAll().size();

        // Create the Compiliance

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompilianceMockMvc.perform(put("/api/compiliances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(compiliance)))
            .andExpect(status().isBadRequest());

        // Validate the Compiliance in the database
        List<Compiliance> compilianceList = compilianceRepository.findAll();
        assertThat(compilianceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompiliance() throws Exception {
        // Initialize the database
        compilianceRepository.saveAndFlush(compiliance);

        int databaseSizeBeforeDelete = compilianceRepository.findAll().size();

        // Delete the compiliance
        restCompilianceMockMvc.perform(delete("/api/compiliances/{id}", compiliance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Compiliance> compilianceList = compilianceRepository.findAll();
        assertThat(compilianceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Compiliance.class);
        Compiliance compiliance1 = new Compiliance();
        compiliance1.setId(1L);
        Compiliance compiliance2 = new Compiliance();
        compiliance2.setId(compiliance1.getId());
        assertThat(compiliance1).isEqualTo(compiliance2);
        compiliance2.setId(2L);
        assertThat(compiliance1).isNotEqualTo(compiliance2);
        compiliance1.setId(null);
        assertThat(compiliance1).isNotEqualTo(compiliance2);
    }
}
