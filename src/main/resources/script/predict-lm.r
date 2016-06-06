sink("/dev/null");
args <- commandArgs(trailingOnly = TRUE)
valueTeamA <- as.numeric(args[1]);
valueTeamB <- as.numeric(args[2]);

lm.home <- readRDS("src/main/resources/script/lm.home.rda")
lm.guest <- readRDS("src/main/resources/script/lm.guest.rda")
lm.diff <- readRDS("src/main/resources/script/lm.diff.rda")

newData <- data.frame(h_complete_value=valueTeamA, g_complete_value=valueTeamB)
predictedGoalsA <- predict(lm.home, newData)
predictedGoalsB <- predict(lm.guest, newData)
predictedGoalsDiff <- predict(lm.diff, newData)

sink()
cat(paste(predictedGoalsA, predictedGoalsB, predictedGoalsDiff))

