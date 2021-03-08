package com.zhuravlov.repairagency.controller.repairFormControllers;

import com.zhuravlov.repairagency.model.DTO.RepairFormDto;
import com.zhuravlov.repairagency.model.builder.RepairFormDtoBuilder;
import com.zhuravlov.repairagency.model.entity.RepairFormEntity;
import com.zhuravlov.repairagency.model.entity.Status;
import com.zhuravlov.repairagency.unit.service.RepairFormService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("User@gmail.com")
@TestPropertySource("/application-test.properties")
class RepairFormControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RepairFormController controller;

    @Autowired
    private RepairFormService service;

    @Test
    void getRepairFormList() throws Exception {
        mockMvc.perform(get("/repairs/list"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());

    }

    @Test
    void addRepairForm() throws Exception {
        mockMvc.perform(get("/repairs/add"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    void saveRepairForm() throws Exception {
        RepairFormDto repairFormDto = new RepairFormDtoBuilder()
                .setAuthorId(3)
                .setCar("car")
                .setShortDescription("short")
                .setDescription("descr")
                .setCreationDate(LocalDateTime.now())
                .build();

        RequestBuilder request = post("/repairs/addRepairForm")
                .flashAttr("repairFormAttribute", repairFormDto)
                .with(csrf());

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/repairs/list"));
    }

    @Test
    void showTicket() throws Exception {
        mockMvc.perform(get("/repairs/view/3"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    void reviewForm() throws Exception {
        mockMvc.perform(get("/repairs/review/3"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().isOk());
    }

    @Test
    void reviewSave() throws Exception {
        RepairFormEntity repairForm = service.getRepairForm(2);
        repairForm.setFeedback("super");

        RequestBuilder request = post("/repairs/saveReview")
                .flashAttr("repairFormAttribute", repairForm)
                .with(csrf());

        mockMvc.perform(request)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/repairs/list"));
    }
}