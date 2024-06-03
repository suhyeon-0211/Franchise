package com.franchise.Franchise.valid;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

import com.franchise.Franchise.valid.ValidationGroups.EnumGroup;
import com.franchise.Franchise.valid.ValidationGroups.NotBlankGroup;

@GroupSequence({Default.class, NotBlankGroup.class, EnumGroup.class})
public interface ValidationSequence {
    
}
