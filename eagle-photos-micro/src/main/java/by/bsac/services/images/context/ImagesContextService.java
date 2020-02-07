package by.bsac.services.images.context;

import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import org.springframework.stereotype.Service;

@Service("ImagesContextService")
public interface ImagesContextService {

    UserImagesContext createUserImagesContext(User a_owner, UserImagesContext a_context);

    UserImagesContext getUserImagesContextById(Integer a_id);

    UserImagesContext getUserImagesContext(UserImagesContext a_context);
}
