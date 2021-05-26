package com.example.bookcatalog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-book-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-book-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithMockUser("admin")
@SpringBootTest
public class BookCatalogApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void contextLoads() throws Exception {
		this.mockMvc.perform(get("/books"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(authenticated())
				.andExpect(content().string(containsString("Каталог книг")));

		this.mockMvc.perform(get("/book/new"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Добавить книгу")));
		this.mockMvc.perform(get("/login?logout"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("Вы вышли из системы.")));
	}

	@Test
	public void bookDeleteRedirectTest() throws Exception{
		this.mockMvc.perform(get("/book/1/delete"))
				.andExpect(authenticated())
				.andExpect(redirectedUrl("/books"));
	}

	@Test
	public void booksListDbTest() throws Exception {
		this.mockMvc.perform(get("/books"))
				.andDo(print())
				.andExpect(authenticated())
				.andExpect(xpath(".//div[@class='content']/div").nodeCount(2));
	}
}
