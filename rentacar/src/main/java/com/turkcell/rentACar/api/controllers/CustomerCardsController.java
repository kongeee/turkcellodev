@RestController
@RequestMapping("/api/customerCards")
public class CustomerCardsController {
   
    private CustomerCardService customerCardService;

    public CustomerCardsController(CustomerCardService customerCardService) 
    {
        this.customerCardService = customerCardService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CustomerCardListDto>> getAll()
    {
        return this.customerCardService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<CustomerCardDto> getById(@RequestParam int id) throws BusinessException
    {
    	return this.customerCardService.getById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCustomerCardRequest createCustomerCardRequest) throws BusinessException 
    {
        return this.customerCardService.add(createCustomerCardRequest);
    }
    
    @PutMapping("/update")
    public Result update(@RequestBody UpdateCustomerCardRequest updateCustomerCardRequest) throws BusinessException
    {
        return this.customerCardService.update(updateCustomerCardRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteCustomerCardRequest deleteCustomerCardRequest) throws BusinessException
    {
        return this.customerCardService.delete(deleteCustomerCardRequest);
    } 
}
