<template>
    <div class="app-container">
        <!--查询表单-->
        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-input v-model="teacherCondition.name" placeholder="讲师名" />
            </el-form-item>

            <el-form-item>
                <el-select v-model="teacherCondition.level" clearable placeholder="讲师头衔">
                    <el-option :value="1" label="高级讲师" />
                    <el-option :value="2" label="首席讲师" />
                </el-select>
            </el-form-item>

            <el-form-item label="添加时间">
                <el-date-picker v-model="teacherCondition.beginTime" type="datetime" placeholder="选择开始时间"
                    value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
            </el-form-item>
            <el-form-item>
                <el-date-picker v-model="teacherCondition.endTime" type="datetime" placeholder="选择截止时间"
                    value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
            </el-form-item>

            <el-button type="primary" icon="el-icon-search" @click="getPageByWrapper()">查询</el-button>
            <el-button type="default" @click="resetData()">清空</el-button>
        </el-form>
        <!-- 表格 v-loading的值设为false ，：data是表格遍历的集合对象-->
        <el-table v-loading="listLoading" :data="teacherList" element-loading-text="数据加载中" border fit
            highlight-current-row>
            <el-table-column label="序号" width="70" align="center">
                <template slot-scope="scope">{{ (pageNum - 1) * pageSize + scope.$index + 1 }}</template>
            </el-table-column>

            <el-table-column prop="name" label="名称" width="80" />

            <el-table-column label="头衔" width="80">
                <template slot-scope="scope">{{ scope.row.level === 1 ? '高级讲师' : '首席讲师' }}</template>
            </el-table-column>

            <el-table-column prop="intro" label="资历" />

            <el-table-column prop="gmtCreate" label="添加时间" width="160" />

            <el-table-column prop="sort" label="排序" width="60" />

            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <router-link :to="'/teacher/edit/' + scope.row.id">
                        <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
                    </router-link>
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeById(scope.row.id)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <!-- 分页 插件：@current-change="getPageByWrapper"：表示分页导航发生改变时触发 getPageByWrapper 这个方法，并携带pageNum参数过去，需要在对应的方法中接受-->
        <el-pagination :current-page="pageNum" :page-size="pageSize" :total="total"
            style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper"
            @current-change="getPageByWrapper" />
    </div>
</template>
<script>
import teacher from "@/api/teacher";

export default {
    data() {
        return {
            teacherList: null, //table表格迭代的集合对象
            listLoading: false, //设置为false页面直接显示数据
            pageNum: 1,
            pageSize: 4,
            total: 0,
            teacherCondition: {} //表单条件对象
        }
    },
    methods: {
        //分页查询所有
        getPage() {
            teacher.getPage(this.pageNum, this.pageSize)
                .then(response => {
                    this.teacherList = response.data.teacherList
                })
        },
        //带条件分页查询 当没有触发分页插件时pageNumUI为未定义状态 所以需要给形参赋值=1
        getPageByWrapper(pageNumUI = 1) {
            this.pageNum = pageNumUI;
            teacher.getPageByWrapper(this.pageNum, this.pageSize, this.teacherCondition)
                .then(response => {
                    this.teacherList = response.data.teacherList;
                    this.total = response.data.total;
                })
        },
        //清空表单item
        resetData() {
            this.teacherCondition = {},
                this.getPageByWrapper()
        },
        //删除讲师
        //2.删除讲师
        removeById(teacherId) {
            this.$confirm("确定下此狠手?", "提示", {
                confirmButtonText: "确定干",
                cancelButtonText: "取消",
                type: "warning"
            })
                .then(() => {
                    teacher.removeById(teacherId).then(response => {
                        //弹框提示删除成功;
                        this.$message({
                            message: "删除成功",
                            type: "success"
                        });
                        //刷新页面重新获得列表信息;
                        this.getPageByWrapper();
                    });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "取消删除"
                    });
                });
        }
    },
    //页面加载完后调用带条件分页查询方法
    created() {
        this.getPageByWrapper()
    }
}
</script>
