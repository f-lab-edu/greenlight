package com.greenlight.global.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.greenlight.global.application.facade.MemberManager;
import com.greenlight.global.application.processor.MemberSignInProcessor;
import com.greenlight.global.application.processor.MemberSignUpProcessor;
import com.greenlight.global.domain.ObjectJsonConverter;
import com.greenlight.global.domain.model.constants.common.GreenLightConstants;
import com.greenlight.global.presentation.request.SignInRequest;
import com.greenlight.global.presentation.request.SignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {

	@Autowired
	public MockMvc mockMvc;

	@MockBean
	private MemberManager memberManager;

	@Test
	@DisplayName("회원가입")
	void signUpTest() throws Exception {
		SignUpRequest signUpRequest = SignUpRequest.builder()
			.memberId("testid")
			.password("12345678a!")
			.nickname("닉네임")
			.phone("01029881245")
			.build();

		MemberSignUpProcessor.Result result = new MemberSignUpProcessor.Result("junhyun");

		//given
		given(memberManager.signUp(any())).willReturn(result);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(GreenLightConstants.PATH_API_VERSION_1+"/member/signUp")
				.content(ObjectJsonConverter.convertToJson(signUpRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
//			.andDo(MockMvcResultHandlers.print())
		;

		//then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").exists())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.memberId").exists())
			.andExpect(jsonPath("$.data.memberId").value("junhyun"))
//			.andDo(MockMvcResultHandlers.print())
		;
	}

	@Test
	@DisplayName("로그인")
	void signInTest() throws Exception {
		SignInRequest signInRequest = SignInRequest.builder()
			.memberId("testid")
			.password("12345678a!")
			.build();

		MemberSignInProcessor.Result result = new MemberSignInProcessor.Result("abcdefg");

		//given
		given(memberManager.signIn(any())).willReturn(result);

		//when
		ResultActions resultActions = mockMvc.perform(
			MockMvcRequestBuilders.post(GreenLightConstants.PATH_API_VERSION_1+"/member/signIn")
				.content(ObjectJsonConverter.convertToJson(signInRequest))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding("UTF-8"))
//			.andDo(MockMvcResultHandlers.print())
			;

		//then
		resultActions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.success").exists())
			.andExpect(jsonPath("$.success").value(true))
			.andExpect(jsonPath("$.data.token").exists())
			.andExpect(jsonPath("$.data.token").value("abcdefg"))
//			.andDo(MockMvcResultHandlers.print())
		;
	}

}
