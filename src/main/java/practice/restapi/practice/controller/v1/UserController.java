package practice.restapi.practice.controller.v1;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import practice.restapi.practice.advice.exception.CUserNotFoundException;
import practice.restapi.practice.entity.User;
import practice.restapi.practice.model.reponse.CommonResult;
import practice.restapi.practice.model.reponse.ListResult;
import practice.restapi.practice.model.reponse.SingleResult;
import practice.restapi.practice.repository.UserJpaRepo;
import practice.restapi.practice.service.ResponseService;

import java.util.List;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1")

public class UserController {

    private final UserJpaRepo userJpaRepo;
    private final ResponseService responseService;


    @ApiImplicitParams(
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true , dataType = "String", paramType = "header")
    )
    @ApiOperation(value = "회원조회",notes = "모든회원을 조회한다.")
    @GetMapping("/users")
    public ListResult<User> findAll(){
        return responseService.getListResult(userJpaRepo.findAll());

    }
    @ApiImplicitParams(
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true , dataType = "String", paramType = "header")
    )
    @ApiOperation(value = "회원 단건 조회", notes = "회원번호로 회원을 조회한다.")
    @GetMapping("/user/{userId}")
    public SingleResult<User> findUserById(
            @ApiParam(value = "회원 ID",required = true) @PathVariable long userId,
            @ApiParam(value = "언어",defaultValue = "en")@RequestParam String lang
            ) throws Exception {
        return responseService.getSingleResult(userJpaRepo.findById(userId).orElseThrow(CUserNotFoundException::new));
    }
    @ApiImplicitParams(
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true , dataType = "String", paramType = "header")
    )
    @ApiOperation(value = "회원 입력", notes = "회원을 입력한다.")
    @PostMapping("/user")
    public SingleResult<User> save(
            @ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원 이름", required = true)@RequestParam String name
    ){
        User user = User.builder().uid(uid).name(name).build();
        return responseService.getSingleResult( userJpaRepo.save(user));
    }
    @ApiImplicitParams(
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true , dataType = "String", paramType = "header")
    )
    @ApiOperation(value = "회원 수정" , notes = "회원정보를 수정한다.")
    @PutMapping("/user")
    public SingleResult<User> modify(
            @ApiParam(value = "회원번호", required = true) @RequestParam Long userId,
            @ApiParam(value = "회원 아이디", required = true) @RequestParam String uid,
            @ApiParam(value = "회원 이름", required = true)@RequestParam String name
    ){
      User user = User.builder()
              .userId(userId)
              .uid(uid)
              .name(name).build();
      return responseService.getSingleResult(userJpaRepo.save(user));
    }
    @ApiImplicitParams(
            @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true , dataType = "String", paramType = "header")
    )
    @ApiOperation(value = "회원 삭제" , notes = "회원번호로 회원을 삭제한다.")
    @DeleteMapping("/user/{userId}")
    public CommonResult delete(
            @ApiParam(value = "회원 번호", required = true) @PathVariable long userId){
        userJpaRepo.deleteById(userId);
        return responseService.getSuccessResult();
    }
}
