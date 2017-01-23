all:
	svm-scale -l 0 -u 1 -s range trainSVM2.t > trainSVM2.s
	svm-scale -r range testSVM2.t > testSVM2.s
	liblinear-train -s 4 trainSVM2.s trainSVM2.model
	liblinear-predict testSVM2.s trainSVM2.model predicts >> accuracy
	rm -rf trainSVM2.model
	cat accuracy

