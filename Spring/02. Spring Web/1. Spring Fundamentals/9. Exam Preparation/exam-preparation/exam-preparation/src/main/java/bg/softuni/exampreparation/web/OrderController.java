package bg.softuni.exampreparation.web;

import bg.softuni.exampreparation.model.binding.OrderAddBindingModel;
import bg.softuni.exampreparation.model.service.OrderServiceModel;
import bg.softuni.exampreparation.service.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute
    public OrderAddBindingModel orderAddBindingModel() {
        return new OrderAddBindingModel();
    }


    @GetMapping("/add")
    public String add(){
        return "order-add";
    }

    @PostMapping("/add")
    public String addConfirm(@Valid OrderAddBindingModel orderAddBindingModel,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("orderAddBindingModel", orderAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.orderAddBindingModel", bindingResult);

        return "redirect:add";
        }

        orderService.addOrder(modelMapper.map(orderAddBindingModel, OrderServiceModel.class));

        return "redirect:/";

    }

}
