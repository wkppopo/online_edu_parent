
import subject from "@/api/subject/subject"
export default {
    data() {
        return {
            //课程分类的集合
            subjectList: [],
            //默认属性映射关系
            defaultProps: {
                label: "title"
            },
            //过滤属性
            filterText: "",
            dialogFormVisible: false,
            subjectUI: {
                title: "",
                parentId: ''
            }
        }
    },
    watch: {
        filterText(val) {
            this.$refs.subjectTree.filter(val);
        }
    },
    methods: {
        //添加一级或者是二级分类
        saveParentOrChildSubject() {
            //说明二级分类id有值
            if(this.subjectUI.parentId){
                this.savChildSubject();
            }else{
                this.savParentSubject();
            }
        },
        //保存一级分类节点信息
        savParentSubject() {
            subject.savParentSubject(this.subjectUI).then(response => {
                //添加成功后弹窗提示
                this.$message({
                    message: "保存成功",
                    type: "success"
                });
                //关闭弹框，
                this.dialogFormVisible = false;
                //刷新页面
                this.getAllSubject();

            })
        },
        //保存二级分类节点信息
        savChildSubject() {
            subject.savChildSubject(this.subjectUI).then(response => {
                //添加成功后弹窗提示
                this.$message({
                    message: "保存成功",
                    type: "success"
                });
                //关闭弹框，
                this.dialogFormVisible = false;
                //刷新页面
                this.getAllSubject();

            })
        },
        //点击一级分类弹窗
        openParentDialog() {
            this.dialogFormVisible = true
            //清空输入内容
            this.subjectUI.title = "";
            //清空二级弹窗赋予的parentID值
            this.subjectUI.parentId="";
        },
        //点击打开二级分类弹窗
        openChildDialog(data) {
            this.dialogFormVisible = true;
            //清空输入内容
            this.subjectUI.title = "";
            //把当前节点id作为新添加节点的父id 
            this.subjectUI.parentId=data.id;
        },
        //树结构过滤方法
        filterNode(value, data) {
            if (!value) return true;
            return data.title.indexOf(value) !== -1;
        },
        getAllSubject() {
            subject.getAllSubject().then(response => {
                this.subjectList = response.data.allSubject;
            })
        },
        deleteSubjectById(node, data) {
            this.$confirm("确定下此狠手?", "提示", {
                confirmButtonText: "确定干",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    subject.deleteSubjectById(data.id).then(response => {
                        //弹窗提示
                        this.$message({
                            message: "删除成功",
                            type: 'success'
                        });
                        //刷新页面
                        this.getAllSubject();
                    })
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "取消删除"
                    });
                });
        }
    },
    created() {
        this.getAllSubject();
    }
}