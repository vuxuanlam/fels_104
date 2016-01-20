<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="container">
    <!-- Form -->
    <form action='<s:url value="listWord"></s:url>' method="POST">
        <!-- Option -->
        <div class="row">
            <div class="col-md-8 col-md-offset-2">
                <div class="col-md-6">
                    <label for="category">Category</label> <select class="form-control"
                        name="categoryIdFilter">
                        <s:iterator value="listCategory">
                            <s:if test="compare(categoryIdFilter,categoryId)">
                                <option value="${categoryId }" selected>${name }</option>
                            </s:if>
                            <s:else>
                                <option value="${categoryId }">${name }</option>
                            </s:else>

                        </s:iterator>
                    </select>
                </div>
                <div class="col-md-6">
                    <s:if test="compare(optionFilter,1)">
                        <label for="options">Options</label>
                        <br>
                        <label class="radio-inline"> <input type="radio" id="inlineRadio1"
                            name="optionFilter" value="1" checked> Learned
                        </label>
                    </s:if>
                    <s:else>
                        <label for="options">Options</label>
                        <br>
                        <label class="radio-inline"> <input type="radio" id="inlineRadio1"
                            name="optionFilter" value="1"> Learned
                        </label>
                    </s:else>
                    <s:if test="compare(optionFilter,2)">
                        <label class="radio-inline"> <input type="radio" name="optionFilter"
                            id="inlineRadio2" value="2" checked> Unlearned
                        </label>
                    </s:if>
                    <s:else>
                        <label class="radio-inline"> <input type="radio" name="optionFilter"
                            id="inlineRadio2" value="2"> Unlearned
                        </label>
                    </s:else>
                    <s:if test="compare(optionFilter,0)">
                        <label class="radio-inline"> <input type="radio" name="optionFilter"
                            id="inlineRadio3" value="0" checked> All
                        </label>
                    </s:if>
                    <s:else>
                        <label class="radio-inline"> <input type="radio" name="optionFilter"
                            id="inlineRadio3" value="0"> All
                        </label>
                    </s:else>
                </div>
            </div>
        </div>
        <div class="row">
            <input class="btn btn-default col-md-offset-8" type="submit" value="Filter">
        </div>
    </form>
    <hr>
    <!-- List Word -->
    <div class="row">
        <div class="col-md-12">
            <c:forEach items="${mapWordAnswer }" var="entry">
                <div class="col-md-3">${entry.key.content }</div>
                <div class="col-md-3">${entry.value.content }</div>
            </c:forEach>
        </div>
    </div>
</div>