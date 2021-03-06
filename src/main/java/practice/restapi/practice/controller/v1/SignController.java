package practice.restapi.practice.controller.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.restapi.practice.advice.exception.CEmailSignInFailedException;
import practice.restapi.practice.config.security.JwtTokenProvider;
import practice.restapi.practice.entity.User;
import practice.restapi.practice.model.reponse.CommonResult;
import practice.restapi.practice.model.reponse.SingleResult;
import practice.restapi.practice.repository.UserJpaRepo;
import practice.restapi.practice.service.ResponseService;

import java.util.Collections;

@Api(tags = {"1,Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {
    private final UserJpaRepo userJpaRepo;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;

    @ApiOperation(value = "로그인", notes = "이메일 회원 로그인을 한다.")
    @PostMapping("/signIn")
    public SingleResult<String> signIn(
            @ApiParam(value = "회원 ID: 이메일" ,required = true) @RequestParam String id,
            @ApiParam(value = "비밀번호", required = true) @RequestParam String password
    ){
        User user = userJpaRepo.findByUid(id).orElseThrow(CEmailSignInFailedException::new);
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new CEmailSignInFailedException();
        }
        return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserId()),user.getRoles()));
    }

    @ApiOperation(value = "가입", notes = "회원가입을 한다")
    @PostMapping("/signUp")
    public CommonResult signUp(
          @ApiParam(value = "회원ID: 이메일",required = true) @RequestParam String id,
          @ApiParam(value = "비밀번호",required = true) @RequestParam String password,
          @ApiParam(value = "이름",required = true) @RequestParam String name
    ){
        userJpaRepo.save(User.builder()
                .uid(id)
                .password(passwordEncoder.encode(password))
                .name(name).roles(Collections.singletonList("ROLE_USER"))
                .build());
        return responseService.getSuccessResult();

    }

}
