package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface UserService {
    Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException;
}
