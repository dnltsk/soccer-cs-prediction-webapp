sink("/dev/null");
require("rpart.plot")
require("Cairo")

#
# function return the given node and all its ancestors (a vector of node numbers)
# http://www.milbo.org/rpart-plot/prp.pdf
#
path.to.root <- function(node)
{
  if(node == 1)   # root?
    node
  else            # recurse, %/% 2 gives the parent of node
    c(node, path.to.root(node %/% 2))
}

#
# find leaf
# https://github.com/cran/treeClust/blob/master/R/rpart.predict.leaves.R
#
rpart.predict.leaves <- function (rp, newdata, type = "where") 
{
  if (type == "where")
    rp$frame$yval <- 1:nrow(rp$frame)
  else if (type == "leaf")
    rp$frame$yval <- rownames(rp$frame)
  else
    stop ("Type must be 'where' or 'leaf'")
  return (predict(rp, newdata=newdata, type = "vector"))
}

args <- commandArgs(trailingOnly = TRUE)

# model created with goals ~ hmid_min_gmid + hmid_min_gdef + hoff_min_gdef + hoff_min_keep + tea_min_tea
newData <- data.frame(hmid_min_gmid = as.numeric(args[1]),
                      hmid_min_gdef = as.numeric(args[2]),
                      hoff_min_gdef = as.numeric(args[3]),
                      hoff_min_keep = as.numeric(args[4]),
                      tea_min_tea = as.numeric(args[5]))

goalsFit <- readRDS("src/main/resources/script/dt.fit.rda")
#get node id
node <- rpart.predict.leaves(goalsFit, newData, type = "where")
nodes <- as.numeric(row.names(goalsFit$frame))
#prinoderee part
cols <- ifelse(nodes %in% path.to.root(node), "sienna", "black")
Cairo(file="src/main/webapp/tmp/tree.png", bg="white", type="png", units="in", width=700/72, height=600/72, dpi=72)
  prp(goalsFit, main="decision tree", #nn=TRUE,
      col=cols, branch.col=cols, split.col=cols, nn.col=cols,
      varlen=0)
dev.off()

#predict goal
predictedGoal <- as.numeric(predict(goalsFit, newData, type="class"))-1

sink()
cat(predictedGoal)
