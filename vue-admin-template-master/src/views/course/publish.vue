<template>
    <div class="app-container">
        <h2 style="text-align: center;">发布新课程</h2>

        <el-steps :active="3" process-status="wait" align-center style="margin-bottom: 40px;">
            <el-step title="填写课程基本信息" />
            <el-step title="创建课程大纲" />
            <el-step title="课程确认" />
        </el-steps>

        <div class="ccInfo">
            <img :src="courseConfirmVo.cover">
            <div class="main">
                <h2>{{ courseConfirmVo.courseName }}</h2>
                <p class="gray">
                    <span>共{{ courseConfirmVo.lessonNum }}课时</span>
                </p>
                <p>
                    <span>所属分类：{{ courseConfirmVo.parentSubjectName }}-{{ courseConfirmVo.childSubjectName }}</span>
                </p>
                <p>课程讲师：{{ courseConfirmVo.teacherName }}</p>
                <h3 class="red">￥{{ courseConfirmVo.price }}</h3>
            </div>
        </div>

        <div>
            <el-button @click="previous">返回修改</el-button>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="publishCourse()">发布课程</el-button>
        </div>
    </div>
</template>
<script>
import course from "@/api/course/course";
export default {
    data() {
        return {
            // 保存按钮是否禁用
            saveBtnDisabled: false,
            courseId: "",
            courseConfirmVo: {}
        };
    },

    created() {
        this.courseId = this.$route.params.courseId;
        this.queryCourseConfirmInfo();
    },

    methods: {
        //1.查询课程发布确认信息
        queryCourseConfirmInfo() {
            course.queryCourseConfirmInfo(this.courseId).then(response => {
                this.courseConfirmVo = response.data.courseConfirmVo;
            });
        },
        //2.课程的发布
        publishCourse() {
            this.$confirm("确定下此狠手?", "提示", {
                confirmButtonText: "确定干",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    course.publishCourse(this.courseId).then(response => {
                        this.$message({
                            type: "success",
                            message: "发布成功"
                        });
                        //发布成功之后跳转到课程列表
                        this.$router.push({ path: "/course/list/" });
                    });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "取消删除"
                    });
                });
        },
        previous() {
            console.log("previous");
            this.$router.push({ path: "/course/chapter/" + this.courseId });
        },

        publish() {
            console.log("publish");
            this.$router.push({ path: "/course/list" });
        }
    }
};
</script>

<style scoped>
.ccInfo {
    background: #f5f5f5;
    padding: 20px;
    overflow: hidden;
    border: 1px dashed #ddd;
    margin-bottom: 40px;
    position: relative;
}

.ccInfo img {
    background: #d6d6d6;
    width: 500px;
    height: 278px;
    display: block;
    float: left;
    border: none;
}

.ccInfo .main {
    margin-left: 520px;
}

.ccInfo .main h2 {
    font-size: 28px;
    margin-bottom: 30px;
    line-height: 1;
    font-weight: normal;
}

.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main p {
    margin-bottom: 10px;
    word-wrap: break-word;
    line-height: 24px;
    max-height: 48px;
    overflow: hidden;
}

.ccInfo .main h3 {
    left: 540px;
    bottom: 20px;
    line-height: 1;
    font-size: 28px;
    color: #d32f24;
    font-weight: normal;
    position: absolute;
}
</style>