sink("/dev/null");
args <- commandArgs(trailingOnly = TRUE)

# model created with 
# goals ~    hmid_min_gmid + hmid_min_gdef + hoff_min_gdef + hoff_min_keep + tea_min_tea
newData <- data.frame(hmid_min_gmid = as.numeric(args[1]),
                      hmid_min_gdef = as.numeric(args[2]),
                      hoff_min_gdef = as.numeric(args[3]),
                      hoff_min_keep = as.numeric(args[4]),
                      tea_min_tea = as.numeric(args[5]))

goalsFit <- readRDS("src/main/resources/script/dt.fit.rda")
predicteGoals <- as.numeric(predict(goalsFit, newData, type="class"))-1

sink()
cat(predicteGoals)

