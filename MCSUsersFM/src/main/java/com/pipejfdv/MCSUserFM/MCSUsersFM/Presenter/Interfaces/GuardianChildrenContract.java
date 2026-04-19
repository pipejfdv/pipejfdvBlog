package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;

import java.util.UUID;

public interface GuardianChildrenContract {
    interface View {
    }

    interface Presenter {
    }

    interface Model {
        GuardianChildren createGuardianChildren(UUID guardian, UUID child, UUID relationship) throws IdNotFoundException;

    }
}
