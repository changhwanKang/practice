package practice.restapi.practice.model.reponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult{

    private T date;
}
