package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.requests.deletes.DeleteUserRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.UserDao;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserManager implements UserService {

    private UserDao userDao;
    
    public UserManager(UserDao userDao, ModelMapper modelMapper) {
        
        this.userDao = userDao;
        
    }

    @Override
    public Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException {
        
        checkIfUserExists(deleteUserRequest.getUserId());
       
        userDao.deleteById(deleteUserRequest.getUserId());
        
        return new SuccessResult("User Deleted");
    }

    private void checkIfUserExists(int userId) throws BusinessException{
        if(!userDao.existsById(userId)){
            throw new BusinessException("User is not found");
        }
    }
    
}
