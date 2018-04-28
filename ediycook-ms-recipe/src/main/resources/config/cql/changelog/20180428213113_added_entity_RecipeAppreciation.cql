
CREATE TABLE IF NOT EXISTS recipeAppreciation (
    id uuid,
    recipeAppreciationAutor text,
    recipeAppreciationVoteValue int,
    recipeAppreciationComment text,
    PRIMARY KEY(id)
);
