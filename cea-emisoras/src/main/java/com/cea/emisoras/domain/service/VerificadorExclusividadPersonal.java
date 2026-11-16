package com.cea.emisoras.domain.service;

import com.cea.emisoras.domain.exception.DatoInvalidoException;
import com.cea.emisoras.domain.exception.PersonalEnVariasProductorasException;
import com.cea.emisoras.domain.valueobject.PersonaId;
import com.cea.emisoras.domain.valueobject.ProductoraId;

import java.util.List;

public class VerificadorExclusividadPersonal {

    public void verificar(PersonaId persona, ProductoraId productoraDelPrograma,
                          List<ProductoraId> productorasDondeYaTrabaja) {
        if (persona == null || productoraDelPrograma == null || productorasDondeYaTrabaja == null) {
            throw new DatoInvalidoException("Se necesitan la persona, la productora y sus productoras actuales.");
        }
        for (ProductoraId otra : productorasDondeYaTrabaja) {
            if (!otra.equals(productoraDelPrograma)) {
                throw new PersonalEnVariasProductorasException(
                        "La persona " + persona + " ya trabaja para otra compañía productora "
                                + "y no puede asignarse a este programa.");
            }
        }
    }
}
