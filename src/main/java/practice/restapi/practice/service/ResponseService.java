package practice.restapi.practice.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.restapi.practice.model.reponse.CommonResult;
import practice.restapi.practice.model.reponse.ListResult;
import practice.restapi.practice.model.reponse.SingleResult;

import java.util.List;

@Service
public class ResponseService {


    /**
     * 코드와 Msg값을 가지는 열거형이다.
     * */
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    public enum CommonResponse{
        SUCCESS(0,"성공하였습니다."),
        FAIL(-1,"실패하였습니다.");

        private int code;
        private String msg;
    }

    //단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> result = new SingleResult<>();
        result.setDate(data);
        setSuccessResult(result);
        return result;
    }

    //여러건 결과를 처리하는 메소드
    public <T>ListResult<T> getListResult(List<T> list){
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }
    // 성공결과만 처리하는 메소드
    public CommonResult getSuccessResult(){
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    //실패결과만 처리하는 메소드
    public CommonResult getFailResult(int code, String msg){
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    private void setSuccessResult (CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }

}
