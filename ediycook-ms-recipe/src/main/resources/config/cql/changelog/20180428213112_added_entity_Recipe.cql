
CREATE TABLE IF NOT EXISTS recipe (
    id uuid,
    recipeName text,
    recipeDescription text,
    recipeCreationDate timestamp,
    recipeModificationDate timestamp,
    recipeCertified boolean,
    recipeCetificationAutor text,
    recipeCertificationStartDate timestamp,
    recipeShared boolean,
    PRIMARY KEY(id)
);
