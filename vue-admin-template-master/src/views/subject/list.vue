<template>
    <div class="app-container">
        <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />
        <el-button type="text" @click="openParentDialog()" >添加一级分类</el-button>
        <el-tree ref="subjectTree" :data="subjectList" :props="defaultProps" :filter-node-method="filterNode"
            :expand-on-click-node="false" class="filter-tree" default-expand-all node-key="id">

            <span class="custom-tree-node" slot-scope="{ node, data }">
                <span>{{ node.label }}</span>
                <span>
                    <el-button v-if="node.level == 1" type="text" size="mini" @click="() => openChildDialog(data)">添加二级分类
                    </el-button>
                    <el-button type="text" size="mini" @click="() => deleteSubjectById(node, data)">删除</el-button>
                </span>
            </span>
        </el-tree>
        <!-- 弹窗 -->
        <el-dialog :visible.sync="dialogFormVisible" title="添加分类">
            <el-form :model="subjectUI" label-width="120px">
                <el-form-item label="分类标题">
                    <el-input v-model="subjectUI.title" />
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveParentOrChildSubject()">确 定</el-button>
                <!-- 测试用 -->
                <!-- <el-button type="primary" @click="savChildSubject()">确 定</el-button> -->
            </div>
        </el-dialog>
    </div>
</template>  
<script src="@/api/subject/list.js"></script>