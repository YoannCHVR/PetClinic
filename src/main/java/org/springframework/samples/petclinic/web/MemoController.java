/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Memo;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
//import org.joda.time.DateTime;
/**
 * @author Helena Berger
 */
@Controller
@SessionAttributes("memo")
public class MemoController {

    private final ClinicService clinicService;
    
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @Autowired
    public MemoController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

   
    
     @RequestMapping(value = "/vets/{vetId}/memos/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("vetId") int vetId, Map<String, Object> model) {
        Vet vet = this.clinicService.findVetById(vetId);
        Memo memo=new Memo();
        vet.addMemo(memo);
        model.put("memo",memo);
        return "memos/createMemoForm";
    }
    
    @RequestMapping(value = "/vets/{vetId}/memos/new", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("memo") Memo memo, BindingResult result, SessionStatus status) {
        new MemoValidator().validate(memo, result);
        if (result.hasErrors()) {
            return "memos/createMemoForm";
        } else {
            this.clinicService.saveMemo(memo);
            status.setComplete();
            return "redirect:/vets/{vetId}";
        }
    }


}