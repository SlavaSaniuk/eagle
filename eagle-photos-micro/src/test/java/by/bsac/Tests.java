package by.bsac;

import by.bsac.domain.models.User;
import by.bsac.domain.models.UserImagesContext;
import by.bsac.repositories.UserCrudRepository;
import by.bsac.services.images.context.UserImagesContextCrudService;
import org.springframework.transaction.annotation.Transactional;

public class Tests {

    @Transactional
    public UserImagesContext createUserContext(UserCrudRepository user_repository, UserImagesContextCrudService context_service) {

        User user = user_repository.save(new User());

        UserImagesContext ctx = new UserImagesContext();
        ctx.setImagesOwner(user);
        user.setImagesContext(ctx);

        return context_service.create(ctx);

    }
}
